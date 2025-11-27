package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerAddressRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerAddressRequest;
import com.resustainability.recollect.dto.response.ICustomerAddressResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.CustomerAddress;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.CustomerAddressRepository;
import com.resustainability.recollect.repository.CustomerRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;
import com.resustainability.recollect.repository.WardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CustomerAddressService {
    private final SecurityService securityService;
    private final CustomerAddressRepository customerAddressRepository;
    private final ScrapRegionRepository scrapRegionRepository;
    private final WardRepository wardRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerAddressService(
            SecurityService securityService,
            CustomerAddressRepository customerAddressRepository,
            ScrapRegionRepository scrapRegionRepository,
            WardRepository wardRepository,
            CustomerRepository customerRepository
    ) {
        this.securityService = securityService;
        this.customerAddressRepository = customerAddressRepository;
        this.scrapRegionRepository = scrapRegionRepository;
        this.wardRepository = wardRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICustomerAddressResponse> list(SearchCriteria searchCriteria) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    customerAddressRepository.findAllPaged(
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


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddCustomerAddressRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (!customerRepository.existsById(request.customerId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER);
        }

        if (null != request.scrapRegionId() && !scrapRegionRepository.existsById(request.scrapRegionId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }

        if (null != request.wardId() && !wardRepository.existsById(request.wardId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }

        return customerAddressRepository.save(
                new CustomerAddress(
                        null,
                        Boolean.TRUE.equals(request.isScrapService()),
                        Boolean.TRUE.equals(request.isScrapLocationActive()),
                        Boolean.TRUE.equals(request.isBioWasteService()),
                        Boolean.TRUE.equals(request.isBioWasteLocationActive()),
                        request.residenceType(),
                        request.residenceDetails(),
                        request.landmark(),
                        request.latitude(),
                        request.longitude(),
                        false,
                        null != request.scrapRegionId() ? scrapRegionRepository.getReferenceById(request.scrapRegionId()) : null,
                        null != request.wardId() ? wardRepository.getReferenceById(request.wardId()) : null,
                        customerRepository.getReferenceById(request.customerId())
                )
        ).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateCustomerAddressRequest request) {
        ValidationUtils.validateRequestBody(request);

        final ICustomerAddressResponse customerAddress = customerAddressRepository
                .findByCustomerAddressId(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));

        final CustomerAddress entity = customerAddressRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));

        final boolean hasCustomerUpdated = !Objects.equals(customerAddress.getCustomerId(), request.customerId());
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
                Boolean.TRUE.equals(request.isScrapService())
        );
        entity.setScrapLocationActive(
                Boolean.TRUE.equals(request.isScrapLocationActive())
        );
        entity.setBioWasteService(
                Boolean.TRUE.equals(request.isBioWasteService())
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
