package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IAdminUserResponse;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.AdminUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserService(
            AdminUserRepository adminUserRepository
    ) {
        this.adminUserRepository = adminUserRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IAdminUserResponse> listAdminUsers(SearchCriteria searchCriteria) {
        return Pager.of(
                adminUserRepository.findAllPaged(
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IAdminUserResponse getById(Long adminUserId) {
        ValidationUtils.validateUserId(adminUserId);
        return adminUserRepository
                .findByAdminUserId(adminUserId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));
    }
}
