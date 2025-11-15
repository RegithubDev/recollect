package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.AdminUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {}
