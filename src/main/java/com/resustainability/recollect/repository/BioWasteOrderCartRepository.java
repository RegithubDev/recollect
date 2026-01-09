package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.BioWasteOrderCart;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioWasteOrderCartRepository extends JpaRepository<BioWasteOrderCart, Long> {
	

}
