package com.resustainability.aakri.service;

import com.resustainability.aakri.entity.AdminEntity;
import com.resustainability.aakri.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(
            AdminRepository adminRepository
    ) {
        this.adminRepository = adminRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }
}
