package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.BioWasteType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioWasteTypeRepository extends JpaRepository<BioWasteType, Long> {
}
