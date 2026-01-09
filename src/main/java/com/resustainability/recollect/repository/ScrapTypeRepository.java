package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapTypeRepository extends JpaRepository<ScrapType, Long> {
	
	Optional<ScrapType> findByIdAndIsActiveTrue(Long id);
}
