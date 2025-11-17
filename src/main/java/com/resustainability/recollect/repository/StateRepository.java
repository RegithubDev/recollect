package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.State;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {}
