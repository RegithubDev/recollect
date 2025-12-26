package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapTypeRepository extends JpaRepository<ScrapType, Long> {
}
