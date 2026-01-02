package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.payload.PayloadLocalBodyAvailability;
import com.resustainability.recollect.dto.request.AddLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateBorderRequest;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.LocalBodyLimitRepository;
import com.resustainability.recollect.repository.LocalBodyRepository;
import com.resustainability.recollect.repository.LocalBodyTypeRepository;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocalBodyService {
    private final GeometryNormalizer geometryNormalizer;
    private final LocalBodyRepository localBodyRepository;
    private final LocalBodyLimitRepository localBodyLimitRepository;
    private final DistrictRepository districtRepository;
    private final LocalBodyTypeRepository localBodyTypeRepository;

    public LocalBodyService(
            GeometryNormalizer geometryNormalizer,
            LocalBodyRepository localBodyRepository,
            LocalBodyLimitRepository localBodyLimitRepository,
            DistrictRepository districtRepository,
            LocalBodyTypeRepository localBodyTypeRepository
    ) {
        this.geometryNormalizer = geometryNormalizer;
        this.localBodyRepository = localBodyRepository;
        this.localBodyLimitRepository = localBodyLimitRepository;
        this.districtRepository = districtRepository;
        this.localBodyTypeRepository = localBodyTypeRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IGeometryResponse getBorderById(Long scrapRegionId) {
        return localBodyRepository
                .findBorderByLocalBodyId(scrapRegionId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) 
    public Pager<ILocalBodyResponse> list(Long districtId, Long stateId, Long countryId, SearchCriteria criteria) {
        return Pager.of(
                localBodyRepository.findAllPaged(
                        criteria.getQ(),
                        districtId,
                        stateId,
                        countryId,
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ILocalBodyResponseByDistrictId> listByDistrict(
            Long districtId,
            SearchCriteria criteria
    ) {

        ValidationUtils.validateDistrictId(districtId);

        Page<ILocalBodyResponseByDistrictId> page =
                localBodyRepository.findAllByDistrictPaged(
                        districtId,
                        criteria.getQ(),
                        criteria.toPageRequest()
                );

        
        if (page.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No local bodies found for the given district"
            );
        }

        return Pager.of(page);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<ILocalBodyLimitResponse> listAvailableDates(
            Long localBodyId,
            LocalDate from,
            LocalDate to
    ) {
        ValidationUtils.validateRangeLimit(from, to, 90);
        return localBodyLimitRepository
                .findAllByLocalBodyIdAndBetween(
                        localBodyId,
                        from,
                        to,
                        true
                );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) 
    public LocalBodyResponse getById(Long id) {
        ValidationUtils.validateId(id);

        final ILocalBodyResponse details = localBodyRepository
                .findByLocalBodyId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        final LocalDate today = LocalDate.now();

        final List<ILocalBodyLimitResponse> availability = localBodyLimitRepository
                .findAllByLocalBodyIdAndBetween(
                        id,
                        today.withDayOfMonth(1),
                        today.withDayOfMonth(today.lengthOfMonth()),
                        false
                );

        return new LocalBodyResponse(details, availability);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) 
    public Long add(AddLocalBodyRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (localBodyRepository.existsByLocalBodyName(request.name())) {
            throw new DataAlreadyExistException(
                    String.format("LocalBody with name (%s) already exists", request.name())
            );
        }

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        LocalBodyType type = localBodyTypeRepository.findById(request.localBodyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY_TYPE));

        LocalBody lb = new LocalBody();
        lb.setLocalBodyName(request.name());
        lb.setDistrict(district);
        lb.setLocalBodyType(type);
        lb.setBorderPolygon(request.borderPolygon());

        lb.setBioProcessingCharge(request.bioProcessingCharge());
        lb.setBioServiceCharge(request.bioServiceCharge());
        lb.setBioSubsidyAmount(request.bioSubsidyAmount());
        lb.setBioCgstPercentage(request.bioCgstPercentage());
        lb.setBioSgstPercentage(request.bioSgstPercentage());
        lb.setBioResidentialPrice(request.bioResidentialPrice());
        lb.setBioCommercialPrice(request.bioCommercialPrice());

        lb.setInclusiveCommercial(request.isInclusiveCommercial());
        lb.setInclusiveResidential(request.isInclusiveResidential());

        lb.setActive(true);
        lb.setDeleted(false);

        lb.setGeometry(
                null == request.geometry() ? null : geometryNormalizer.toMultiPolygon(request.geometry())
        );

        LocalBody saved = localBodyRepository.save(lb);

        final List<LocalBodyLimit> regionAvailabilities = request
                .availability()
                .stream()
                .filter(dto -> null != dto.date() && null != dto.limit() && dto.limit() >= 0)
                .collect(Collectors.toMap(
                        PayloadLocalBodyAvailability::date,
                        Function.identity(),
                        (existing, replacement) -> replacement
                )).values()
                .stream()
                .map(dto -> new LocalBodyLimit(
                        null,
                        dto.date(),
                        dto.limit(),
                        dto.limit(),
                        saved
                ))
                .toList();

        if (regionAvailabilities.isEmpty()) {
            throw new InvalidDataException("Provide availability pickup dates for current and next month");
        }

        localBodyLimitRepository.saveAll(regionAvailabilities);

        return saved.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) 
    public void update(UpdateLocalBodyRequest request) {

        ValidationUtils.validateRequestBody(request);

        LocalBody lb = localBodyRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        lb.setLocalBodyName(request.name());
        lb.setBorderPolygon(request.borderPolygon());
        lb.setActive(Boolean.TRUE.equals(request.isActive()));

        lb.setBioProcessingCharge(request.bioProcessingCharge());
        lb.setBioServiceCharge(request.bioServiceCharge());
        lb.setBioSubsidyAmount(request.bioSubsidyAmount());
        lb.setBioCgstPercentage(request.bioCgstPercentage());
        lb.setBioSgstPercentage(request.bioSgstPercentage());
        lb.setBioResidentialPrice(request.bioResidentialPrice());
        lb.setBioCommercialPrice(request.bioCommercialPrice());

        lb.setInclusiveCommercial(request.isInclusiveCommercial());
        lb.setInclusiveResidential(request.isInclusiveResidential());

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        LocalBodyType type = localBodyTypeRepository.findById(request.localBodyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY_TYPE));

        lb.setDistrict(district);
        lb.setLocalBodyType(type);

        localBodyRepository.save(lb);


        final Map<LocalDate, PayloadLocalBodyAvailability> indexedRegionAvailability = request
                .availability()
                .stream()
                .filter(dto -> null != dto.date() && null != dto.limit() && dto.limit() >= 0)
                .collect(Collectors.toMap(
                        PayloadLocalBodyAvailability::date,
                        Function.identity(),
                        (existing, replacement) -> replacement
                ));

        final Map<LocalDate, LocalBodyLimit> existingRegionAvailabilities = localBodyLimitRepository
                .findAllByLocalBodyIdAndDates(request.id(), indexedRegionAvailability.keySet())
                .stream()
                .collect(Collectors.toMap(
                        LocalBodyLimit::getAvailableDate,
                        Function.identity(),
                        (existing, replacement) -> replacement
                ));

        final List<LocalBodyLimit> entities = indexedRegionAvailability
                .values()
                .stream()
                .map(dto -> existingRegionAvailabilities.compute(dto.date(), (k, v) -> {
                    if (null == v) {
                        return new LocalBodyLimit(
                                null,
                                dto.date(),
                                dto.limit(),
                                dto.limit(),
                                lb
                        );
                    }

                    v.setLimit(dto.limit());
                    v.setRemainingSlots(
                            Math.max(0, dto.limit() - (v.getLimit() - v.getRemainingSlots()))
                    ); // TODO: compute logic
                    return v;
                }))
                .toList();

        if (!entities.isEmpty()) {
            localBodyLimitRepository.saveAll(entities);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateBorder(UpdateBorderRequest request) {
        ValidationUtils.validateRequestBody(request);

        final LocalBody entity = localBodyRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        entity.setGeometry(
                geometryNormalizer.toMultiPolygon(request.geometry())
        );
        localBodyRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void normalizeAllToGeometry() {
        final List<LocalBody> entities = localBodyRepository
                .findAll()
                .stream()
                .filter(entity -> null == entity.getGeometry() && StringUtils.isNotBlank(entity.getBorderPolygon()))
                .map(entity -> {
                    entity.setGeometry(
                            geometryNormalizer.parseToMultiPolygon(
                                    entity.getBorderPolygon()
                            )
                    );
                    return entity;
                })
                .toList();
        localBodyRepository.saveAll(entities);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void toggleById(Long localBodyId) {
        ValidationUtils.validateId(localBodyId);

        if (0 == localBodyRepository.toggleActiveStatusById(localBodyId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) 
    public void deleteById(Long id, boolean value) {
        ValidationUtils.validateId(id);

        if (0 == localBodyRepository.deleteLocalBodyById(id, !value, value)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY);
        }
    }
}
