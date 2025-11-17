package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.dto.response.BioWasteCategoryResponse;
import com.resustainability.recollect.dto.response.BioWasteTypeResponse;
import com.resustainability.recollect.dto.response.IBioWasteCategoryTypeResponse;
import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.repository.BioWasteCategoryRepository;
import com.resustainability.recollect.repository.ServiceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MobileService {
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final BioWasteCategoryRepository bioWasteCategoryRepository;

    @Autowired
    public MobileService(
            ServiceCategoryRepository serviceCategoryRepository,
            BioWasteCategoryRepository bioWasteCategoryRepository
    ) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.bioWasteCategoryRepository = bioWasteCategoryRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IServiceCategoryResponse> listServices() {
        return serviceCategoryRepository.findAllActiveServices();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Collection<BioWasteCategoryResponse> listBioWasteCategories() {
        final List<IBioWasteCategoryTypeResponse> rows = bioWasteCategoryRepository
                .findAllActiveCategoryTypes();

        if (CollectionUtils.isBlank(rows)) {
            return Collections.emptyList();
        }

        final Map<Long, BioWasteCategoryResponse> categories = new LinkedHashMap<>();

        for (final IBioWasteCategoryTypeResponse r : rows) {
            categories.computeIfAbsent(
                    r.getCategoryId(),
                    id -> new BioWasteCategoryResponse(
                            id,
                            r.getCategoryName(),
                            r.getCategoryIcon(),
                            Boolean.TRUE.equals(r.getCategoryIsActive()),
                            new ArrayList<>()
                    )
            ).types().add(
                    new BioWasteTypeResponse(
                            r.getTypeId(),
                            r.getTypeName(),
                            r.getTypeIcon(),
                            Boolean.TRUE.equals(r.getTypeIsActive())
                    )
            );
        }

        return categories.values();
    }
}
