package com.resustainability.recollect.service;

import com.resustainability.recollect.entity.backend.AdminUser;
import com.resustainability.recollect.repository.AdminUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public AdminService(
            AdminUserRepository adminUserRepository
    ) {
        this.adminUserRepository = adminUserRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<AdminUser> findAll() {
        return adminUserRepository.findAll();
    }
}
