package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.Ward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {}
