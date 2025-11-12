package com.resustainability.aakri.repository;

import com.resustainability.aakri.entity.AdminEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {}
