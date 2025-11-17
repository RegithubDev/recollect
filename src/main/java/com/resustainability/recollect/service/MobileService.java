package com.resustainability.recollect.service;

import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.repository.ServiceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MobileService {
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    public MobileService(
            ServiceCategoryRepository serviceCategoryRepository
    ) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IServiceCategoryResponse> listServices() {
        return serviceCategoryRepository.findAllActiveServices();
    }
}
