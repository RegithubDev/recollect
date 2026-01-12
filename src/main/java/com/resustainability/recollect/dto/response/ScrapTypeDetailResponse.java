package com.resustainability.recollect.dto.response;

import java.util.List;

public class ScrapTypeDetailResponse {

    private Long id;
    private String scrapName;
    private String image;
    private Boolean isPayable;
    private Boolean isKg;
    private Boolean isActive;
    private Long categoryId;
    private String categoryName;

    private List<IScrapTypeDistrictPriceResponse> districtPrices;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScrapName() {
		return scrapName;
	}

	public void setScrapName(String scrapName) {
		this.scrapName = scrapName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getIsPayable() {
		return isPayable;
	}

	public void setIsPayable(Boolean isPayable) {
		this.isPayable = isPayable;
	}

	public Boolean getIsKg() {
		return isKg;
	}

	public void setIsKg(Boolean isKg) {
		this.isKg = isKg;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<IScrapTypeDistrictPriceResponse> getDistrictPrices() {
		return districtPrices;
	}

	public void setDistrictPrices(List<IScrapTypeDistrictPriceResponse> districtPrices) {
		this.districtPrices = districtPrices;
	}

   
}
