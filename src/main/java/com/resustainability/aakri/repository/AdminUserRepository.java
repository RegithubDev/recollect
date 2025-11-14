package com.resustainability.aakri.repository;

import com.resustainability.aakri.entity.backend.AdminUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {}
