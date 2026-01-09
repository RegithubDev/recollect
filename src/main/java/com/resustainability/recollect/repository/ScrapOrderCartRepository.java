package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapOrderCart;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ScrapOrderCartRepository extends JpaRepository<ScrapOrderCart, Long> {
	

}
