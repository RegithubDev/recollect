package com.resustainability.recollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resustainability.recollect.entity.backend.BwgClient;

@Repository
public interface BwgClientRepository extends JpaRepository<BwgClient, Long>{

}
