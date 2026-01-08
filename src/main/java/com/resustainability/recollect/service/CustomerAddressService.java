package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerAddressRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerAddressRequest;
import com.resustainability.recollect.dto.response.BoundariesResponse;
import com.resustainability.recollect.dto.response.ICustomerAddressResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.entity.backend.CustomerAddress;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.locationtech.jts.geom.MultiPolygon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerAddressService {
    private final GeometryNormalizer geometryNormalizer;
    private final SecurityService securityService;
    private final CustomerAddressRepository customerAddressRepository;
    private final ScrapRegionRepository scrapRegionRepository;
    private final LocalBodyRepository localBodyRepository;
    private final WardRepository wardRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerAddressService(
            GeometryNormalizer geometryNormalizer,
            SecurityService securityService,
            CustomerAddressRepository customerAddressRepository,
            ScrapRegionRepository scrapRegionRepository,
            LocalBodyRepository localBodyRepository,
            WardRepository wardRepository,
            CustomerRepository customerRepository
    ) {
        this.geometryNormalizer = geometryNormalizer;
        this.securityService = securityService;
        this.customerAddressRepository = customerAddressRepository;
        this.scrapRegionRepository = scrapRegionRepository;
        this.localBodyRepository = localBodyRepository;
        this.wardRepository = wardRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public MultiPolygon loadMergedScrapRegionBoundaries() {
        try (var stream = scrapRegionRepository.streamAllActiveGeometries()) {
            return geometryNormalizer.merge(
                    stream.filter(Objects::nonNull)
                            .map(geometryNormalizer::toMultiPolygon)
                            .toList()
            );
        }
    }

    @Transactional(readOnly = true)
    public MultiPolygon loadMergedLocalBodyBoundaries() {
        try (var stream = localBodyRepository.streamAllActiveGeometries()) {
            return geometryNormalizer.merge(
                    stream.filter(Objects::nonNull)
                            .map(geometryNormalizer::toMultiPolygon)
                            .toList()
            );
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Void evaluateAddressesUsingBoundaryGeometry(
            MultiPolygon scrapRegionBoundaries,
            MultiPolygon localBodyBoundaries
    ) {
        final int FETCH_CHUNK_SIZE = 500;
        final int MAX_BATCH_SIZE = 50_00_000;

        long processedCount = 0;
        final PageRequest pageRequest = PageRequest.of(0, FETCH_CHUNK_SIZE);
        while (processedCount < MAX_BATCH_SIZE) {
            final Page<CustomerAddress> page = customerAddressRepository
                    .findAll(pageRequest);

            if (!page.hasContent()) {
                break;
            }

            final Map<Long, CustomerAddress> indexedEntities = page.get()
                    .filter(entity -> StringUtils.isNotBlank(entity.getLatitude()) && StringUtils.isNotBlank(entity.getLongitude()))
                    .collect(Collectors.toMap(
                            CustomerAddress::getId,
                            Function.identity(),
                            (existing, replacement) -> existing
                    ));

            if (indexedEntities.isEmpty()) {
                break;
            }

            final List<CustomerAddress> entitiesToSave = indexedEntities
                    .values()
                    .stream()
                    .peek(entity -> {
                        final double[] coordinates = ValidationUtils
                                .validateAndParseCoordinates(
                                        entity.getLatitude(),
                                        entity.getLongitude()
                                );

                        entity.setScrapService(
                                geometryNormalizer.isPointInsideBoundary(
                                        coordinates[0],
                                        coordinates[1],
                                        scrapRegionBoundaries
                                )
                        );
                        entity.setBioWasteService(
                                geometryNormalizer.isPointInsideBoundary(
                                        coordinates[0],
                                        coordinates[1],
                                        localBodyBoundaries
                                )
                        );
                    })
                    .toList();

            if (CollectionUtils.isNonBlank(entitiesToSave)) {
                customerAddressRepository.saveAll(entitiesToSave);
            }

            processedCount += page.getNumberOfElements();
        }
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICustomerAddressResponse> list(Long customerId, SearchCriteria searchCriteria) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    customerAddressRepository.findAllPaged(
                            customerId,
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        } else if (Boolean.TRUE.equals(user.getIsCustomer())) {
            return Pager.of(
                    customerAddressRepository.findAllPagedIfBelongs(
                            user.getId(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        }

        return Pager.empty(pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<ICustomerAddressResponse> listAllTrackData(String searchTerm) {
        return customerAddressRepository.findAllTrackData(searchTerm);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BoundariesResponse listAllContainingGeometry(String latitude, String longitude) {
        final double[] coordinates = ValidationUtils
                .validateAndParseCoordinates(latitude, longitude);
        return new BoundariesResponse(
                scrapRegionRepository
                        .findIdsContainingGeometry(
                                coordinates[0],
                                coordinates[1],
                                GeometryNormalizer.SRID
                        ),
                localBodyRepository
                        .findIdsContainingGeometry(
                                coordinates[0],
                                coordinates[1],
                                GeometryNormalizer.SRID
                        )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ICustomerAddressResponse getById(Long customerAddressId) {
        ValidationUtils.validateId(customerAddressId);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            return customerAddressRepository
                    .findByCustomerAddressIdIfBelongs(user.getId(), customerAddressId)
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));
        }

        return customerAddressRepository
                .findByCustomerAddressId(customerAddressId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));
    }

    public boolean isInScrapRegionBoundaries(String latitude, String longitude, Long scrapRegionId) {
        if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude) || null == scrapRegionId) {
            return false;
        }
        final double[] coordinates = ValidationUtils
                .validateAndParseCoordinates(latitude, longitude);
        return 1L == scrapRegionRepository.existsContainingGeometryById(
                scrapRegionId,
                coordinates[0],
                coordinates[1],
                GeometryNormalizer.SRID
        );
    }

    public boolean isInWardBoundaries(String latitude, String longitude, Long wardId) {
        final Long localBodyId;
        if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude) || null == wardId || null == (localBodyId = wardRepository.findLocalBodyIdById(wardId))) {
            return false;
        }
        final double[] coordinates = ValidationUtils
                .validateAndParseCoordinates(latitude, longitude);
        return 1L == localBodyRepository.existsContainingGeometryById(
                localBodyId,
                coordinates[0],
                coordinates[1],
                GeometryNormalizer.SRID
        );
    }

    public boolean isInLocalBodyBoundaries(String latitude, String longitude, Long localBodyId) {
        if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude) || null == localBodyId) {
            return false;
        }
        final double[] coordinates = ValidationUtils
                .validateAndParseCoordinates(latitude, longitude);
        return 1L == localBodyRepository.existsContainingGeometryById(
                localBodyId,
                coordinates[0],
                coordinates[1],
                GeometryNormalizer.SRID
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddCustomerAddressRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Customer customer;
        if (!Boolean.TRUE.equals(user.getIsCustomer())) {

            ValidationUtils.validateUserId(request.customerId());

            if (!customerRepository.existsById(request.customerId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER);
            }

            customer = customerRepository.getReferenceById(request.customerId());
        } else {
            customer = customerRepository.getReferenceById(user.getId());
        }

        if (request.scrapRegionId() != null &&
                !scrapRegionRepository.existsById(request.scrapRegionId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }

        if (request.wardId() != null &&
                !wardRepository.existsById(request.wardId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }

        return customerAddressRepository.save(
                new CustomerAddress(
                        null,
                        isInScrapRegionBoundaries(
                                request.latitude(),
                                request.longitude(),
                                request.scrapRegionId()
                        ),
                        Boolean.TRUE.equals(request.isScrapLocationActive()),
                        isInWardBoundaries(
                                request.latitude(),
                                request.longitude(),
                                request.wardId()
                        ),
                        Boolean.TRUE.equals(request.isBioWasteLocationActive()),
                        request.residenceType(),
                        request.residenceDetails(),
                        request.landmark(),
                        request.latitude(),
                        request.longitude(),
                        false,
                        request.scrapRegionId() != null
                                ? scrapRegionRepository.getReferenceById(request.scrapRegionId())
                                : null,
                        request.wardId() != null
                                ? wardRepository.getReferenceById(request.wardId())
                                : null,
                        customer
                )
        ).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateCustomerAddressRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final ICustomerAddressResponse customerAddress = customerAddressRepository
                .findByCustomerAddressId(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));

        final CustomerAddress entity = customerAddressRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));

        final boolean hasCustomerUpdated = !Boolean.TRUE.equals(user.getIsCustomer()) && !Objects.equals(customerAddress.getCustomerId(), request.customerId());
        final boolean hasScrapRegionUpdated = !Objects.equals(customerAddress.getScrapRegionId(), request.scrapRegionId());
        final boolean hasWardUpdated = !Objects.equals(customerAddress.getWardId(), request.wardId());

        if (hasCustomerUpdated) {
            if (!customerRepository.existsById(request.customerId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER);
            } else {
                entity.setCustomer(
                        customerRepository.getReferenceById(request.customerId())
                );
            }
        }

        if (hasScrapRegionUpdated) {
            if (null == request.scrapRegionId()) {
                entity.setScrapRegion(null);
            } else if (!scrapRegionRepository.existsById(request.scrapRegionId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
            } else {
                entity.setScrapRegion(
                        scrapRegionRepository.getReferenceById(request.scrapRegionId())
                );
            }
        }

        if (hasWardUpdated) {
            if (null == request.wardId()) {
                entity.setWard(null);
            } else if (!wardRepository.existsById(request.wardId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
            } else {
                entity.setWard(
                        wardRepository.getReferenceById(request.wardId())
                );
            }
        }

        entity.setScrapService(
                isInScrapRegionBoundaries(
                        request.latitude(),
                        request.longitude(),
                        request.scrapRegionId()
                )
        );
        entity.setScrapLocationActive(
                Boolean.TRUE.equals(request.isScrapLocationActive())
        );
        entity.setBioWasteService(
                isInWardBoundaries(
                        request.latitude(),
                        request.longitude(),
                        request.wardId()
                )
        );
        entity.setBioWasteLocationActive(
                Boolean.TRUE.equals(request.isBioWasteLocationActive())
        );
        entity.setResidenceType(request.residenceType());
        entity.setResidenceDetails(request.residenceDetails());
        entity.setLandmark(request.landmark());
        entity.setLatitude(request.latitude());
        entity.setLongitude(request.longitude());

        customerAddressRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long customerAddressId, boolean value) {
        ValidationUtils.validateId(customerAddressId);
        if (0 == customerAddressRepository.deleteCustomerAddressById(
                customerAddressId,
                value
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS);
        }
    }
}
