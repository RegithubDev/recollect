package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapRegion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRegionRepository extends JpaRepository<ScrapRegion, Long> {}
