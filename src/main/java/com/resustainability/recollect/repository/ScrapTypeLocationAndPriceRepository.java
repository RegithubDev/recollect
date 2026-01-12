package com.resustainability.recollect.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.resustainability.recollect.dto.response.IScrapTypeDistrictPriceResponse;
import com.resustainability.recollect.entity.backend.ScrapTypeLocationAndPrice;

public interface ScrapTypeLocationAndPriceRepository extends JpaRepository<ScrapTypeLocationAndPrice, Long> {

	 @Query("""
		        SELECT s
		        FROM ScrapTypeLocationAndPrice s
		        WHERE s.scrapType.id = :scrapTypeId
		          AND s.district.id = :districtId
		    """)
		    Optional<ScrapTypeLocationAndPrice> findByScrapTypeAndDistrict(
		            @Param("scrapTypeId") Long scrapTypeId,
		            @Param("districtId") Long districtId
		    );
	 
	  @Query("""
		        SELECT
		            d.id AS districtId,
		            d.districtName AS districtName,
		            p.scrapPrice AS scrapPrice,
		            p.scrapCgst AS scrapCgst,
		            p.scrapSgst AS scrapSgst,
		            p.isActive AS isActive
		        FROM ScrapTypeLocationAndPrice p
		        JOIN p.district d
		        WHERE p.scrapType.id = :scrapTypeId
		    """)
		    List<IScrapTypeDistrictPriceResponse> findByScrapTypeId(
		            @Param("scrapTypeId") Long scrapTypeId
		    );

}
