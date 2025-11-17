package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.BioWasteCategoryRepository;
import com.resustainability.recollect.repository.ScrapCategoryRepository;
import com.resustainability.recollect.repository.ServiceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MobileService {
    private final SecurityService securityService;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final BioWasteCategoryRepository bioWasteCategoryRepository;
    private final ScrapCategoryRepository scrapCategoryRepository;

    @Autowired
    public MobileService(
            SecurityService securityService,
            ServiceCategoryRepository serviceCategoryRepository,
            BioWasteCategoryRepository bioWasteCategoryRepository,
            ScrapCategoryRepository scrapCategoryRepository
    ) {
        this.securityService = securityService;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.bioWasteCategoryRepository = bioWasteCategoryRepository;
        this.scrapCategoryRepository = scrapCategoryRepository;
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
                            new ArrayList<>()
                    )
            ).types().add(
                    new BioWasteTypeResponse(
                            r.getTypeId(),
                            r.getTypeName(),
                            r.getTypeIcon()
                    )
            );
        }

        return categories.values();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Collection<ScrapCategoryResponse> listScrapCategories() {
        final Long districtId = securityService
                .getCurrentUser()
                .map(IUserContext::getDistrictId)
                .orElse(null);

        final List<IScrapCategoryTypeResponse> rows = scrapCategoryRepository
                .findAllActiveCategoryTypes(districtId);

        if (CollectionUtils.isBlank(rows)) {
            return Collections.emptyList();
        }

        final Map<Long, ScrapCategoryResponse> categories = new LinkedHashMap<>();

        for (final IScrapCategoryTypeResponse r : rows) {
            categories.computeIfAbsent(
                    r.getCategoryId(),
                    id -> new ScrapCategoryResponse(
                            id,
                            r.getCategoryName(),
                            r.getSubcategoryName(),
                            r.getCategoryIcon(),
                            new ArrayList<>()
                    )
            ).types().add(
                    new ScrapTypeResponse(
                            r.getTypeId(),
                            r.getTypeName(),
                            r.getTypeIcon(),
                            r.getTypeIsPayable(),
                            r.getTypeIsKg(),
                            r.getPrice(),
                            r.getCgst(),
                            r.getSgst()
                    )
            );
        }

        return categories.values();
    }
}
