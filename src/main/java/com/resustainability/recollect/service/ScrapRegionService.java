package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.payload.PayloadScrapRegionAvailability;
import com.resustainability.recollect.dto.request.AddScrapRegionRequest;
import com.resustainability.recollect.dto.request.UpdateScrapRegionBorderRequest;
import com.resustainability.recollect.dto.request.UpdateScrapRegionRequest;
import com.resustainability.recollect.dto.response.IGeometryResponse;
import com.resustainability.recollect.dto.response.IScrapRegionAvailabilityResponse;
import com.resustainability.recollect.dto.response.IScrapRegionResponse;
import com.resustainability.recollect.dto.response.ScrapRegionResponse;
import com.resustainability.recollect.entity.backend.ScrapRegion;
import com.resustainability.recollect.entity.backend.ScrapRegionAvailability;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.ScrapRegionAvailabilityRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScrapRegionService {
    private final GeometryNormalizer geometryNormalizer;
    private final ScrapRegionRepository scrapRegionRepository;
    private final ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository;
    private final DistrictRepository districtRepository;

    @Autowired
    public ScrapRegionService(
            GeometryNormalizer geometryNormalizer,
            ScrapRegionRepository scrapRegionRepository,
            ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository,
            DistrictRepository districtRepository
    ) {
        this.geometryNormalizer = geometryNormalizer;
        this.scrapRegionRepository = scrapRegionRepository;
        this.scrapRegionAvailabilityRepository = scrapRegionAvailabilityRepository;
        this.districtRepository = districtRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IGeometryResponse getBorderById(Long scrapRegionId) {
        return scrapRegionRepository
                .findBorderByScrapRegionId(scrapRegionId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IScrapRegionResponse> list(SearchCriteria searchCriteria) {
        return Pager.of(
                scrapRegionRepository.findAllPaged(
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequestUnLimit()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IScrapRegionAvailabilityResponse> listAvailableDates(
            Long scrapRegionId,
            LocalDate from,
            LocalDate to
    ) {
        ValidationUtils.validateRangeLimit(from, to, 90);
        return scrapRegionAvailabilityRepository
                .findAllByScrapRegionIdAndBetween(
                        scrapRegionId,
                        from,
                        to,
                        true
                );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ScrapRegionResponse getById(Long scrapRegionId) {
        ValidationUtils.validateId(scrapRegionId);

        final IScrapRegionResponse details = scrapRegionRepository
                .findByScrapRegionId(scrapRegionId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        final LocalDate today = LocalDate.now();

        final List<IScrapRegionAvailabilityResponse> availability = scrapRegionAvailabilityRepository
                .findAllByScrapRegionIdAndBetween(
                        scrapRegionId,
                        today.withDayOfMonth(1),
                        today.withDayOfMonth(today.lengthOfMonth()),
                        false
                );

        return new ScrapRegionResponse(details, availability);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void add(AddScrapRegionRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (!districtRepository.existsById(request.districtId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT);
        }

        final ScrapRegion entity = scrapRegionRepository.save(
                new ScrapRegion(
                        null,
                        request.name(),
                        null,
                        request.currentWeekDay(),
                        request.nextWeekDay(),
                        true,
                        false,
                        districtRepository.getReferenceById(request.districtId()),
                        geometryNormalizer.toMultiPolygon(request.geometry())
                )
        );

        final List<ScrapRegionAvailability> regionAvailabilities = request
                .availability()
                .stream()
                .filter(dto -> null != dto.date() && null != dto.limit() && dto.limit() >= 0)
                .collect(Collectors.toMap(
                        PayloadScrapRegionAvailability::date,
                        Function.identity(),
                        (existing, replacement) -> replacement
                )).values()
                .stream()
                .map(dto -> new ScrapRegionAvailability(
                        null,
                        dto.date(),
                        dto.limit(),
                        dto.limit(),
                        entity
                ))
                .toList();

        if (regionAvailabilities.isEmpty()) {
            throw new InvalidDataException("Provide availability pickup dates for current and next month");
        }

        scrapRegionAvailabilityRepository.saveAll(regionAvailabilities);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateScrapRegionRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IScrapRegionResponse scrapRegion = scrapRegionRepository
                .findByScrapRegionId(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        final ScrapRegion entity = scrapRegionRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        final boolean hasDistrictUpdated = !Objects.equals(scrapRegion.getDistrictId(), request.districtId());

        if (hasDistrictUpdated) {
            if (!districtRepository.existsById(request.districtId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT);
            } else {
                entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
            }
        }

        entity.setRegionName(request.name());
        entity.setRegionWeekdayCurrent(request.currentWeekDay());
        entity.setRegionWeekdayNext(request.nextWeekDay());
        scrapRegionRepository.save(entity);

        final Map<LocalDate, PayloadScrapRegionAvailability> indexedRegionAvailability = request
                .availability()
                .stream()
                .filter(dto -> null != dto.date() && null != dto.limit() && dto.limit() >= 0)
                .collect(Collectors.toMap(
                        PayloadScrapRegionAvailability::date,
                        Function.identity(),
                        (existing, replacement) -> replacement
                ));

        final Map<LocalDate, ScrapRegionAvailability> existingRegionAvailabilities = scrapRegionAvailabilityRepository
                .findAllByScrapRegionIdAndDates(request.id(), indexedRegionAvailability.keySet())
                .stream()
                .collect(Collectors.toMap(
                        ScrapRegionAvailability::getAvailableDate,
                        Function.identity(),
                        (existing, replacement) -> replacement
                ));

        final List<ScrapRegionAvailability> entities = indexedRegionAvailability
                .values()
                .stream()
                .map(dto -> existingRegionAvailabilities.compute(dto.date(), (k, v) -> {
                    if (null == v) {
                        return new ScrapRegionAvailability(
                                null,
                                dto.date(),
                                dto.limit(),
                                dto.limit(),
                                entity
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
            scrapRegionAvailabilityRepository.saveAll(entities);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateBorder(UpdateScrapRegionBorderRequest request) {
        ValidationUtils.validateRequestBody(request);

        final ScrapRegion entity = scrapRegionRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        entity.setGeometry(
                geometryNormalizer.toMultiPolygon(request.geometry())
        );
        scrapRegionRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void normalizeAllToGeometry() {
        final List<ScrapRegion> entities = scrapRegionRepository
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
        scrapRegionRepository.saveAll(entities);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void toggleById(Long scrapRegionId) {
        ValidationUtils.validateId(scrapRegionId);

        if (0 == scrapRegionRepository.toggleActiveStatusById(scrapRegionId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long scrapRegionId, boolean isDeleted) {
        ValidationUtils.validateId(scrapRegionId);

        if (0 == scrapRegionRepository.deleteScrapRegionById(
                scrapRegionId, !isDeleted, isDeleted
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }
    }
}
