package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.District;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {}
