package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateBorderRequest;
import com.resustainability.recollect.dto.response.IGeometryResponse;
import com.resustainability.recollect.dto.response.ILocalBodyResponse;
import com.resustainability.recollect.dto.response.ILocalBodyResponseByDistrictId;
import com.resustainability.recollect.entity.backend.District;
import com.resustainability.recollect.entity.backend.LocalBody;
import com.resustainability.recollect.entity.backend.LocalBodyType;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.LocalBodyRepository;
import com.resustainability.recollect.repository.LocalBodyTypeRepository;
import com.resustainability.recollect.util.GeometryNormalizer;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Service
public class LocalBodyService {
    private final GeometryNormalizer geometryNormalizer;
    private final LocalBodyRepository localBodyRepository;
    private final DistrictRepository districtRepository;
    private final LocalBodyTypeRepository localBodyTypeRepository;

    public LocalBodyService(
            GeometryNormalizer geometryNormalizer,
            LocalBodyRepository localBodyRepository,
            DistrictRepository districtRepository,
            LocalBodyTypeRepository localBodyTypeRepository
    ) {
        this.geometryNormalizer = geometryNormalizer;
        this.localBodyRepository = localBodyRepository;
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
    public ILocalBodyResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return localBodyRepository.findByLocalBodyId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));
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

        // TODO - regionAvailabilities

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
