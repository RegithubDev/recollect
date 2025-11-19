package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCountryRequest;
import com.resustainability.recollect.dto.request.UpdateCountryRequest;
import com.resustainability.recollect.dto.response.ICountryResponse;
import com.resustainability.recollect.entity.backend.Country;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(
            CountryRepository countryRepository
    ) {
        this.countryRepository = countryRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICountryResponse> list(SearchCriteria searchCriteria) {
        return Pager.of(
                countryRepository.findAllPaged(
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ICountryResponse getById(Long countryId) {
        ValidationUtils.validateId(countryId);
        return countryRepository
                .findByCountryId(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY));
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void add(AddCountryRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (countryRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("Country with (%s) already exists", request.code())
            );
        }

        countryRepository.save(
                new Country(
                        null,
                        request.name(),
                        request.code(),
                        null,
                        true,
                        false
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateCountryRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Country entity = countryRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY));

        final boolean hasCodeUpdated = !entity.getCountryCode().equalsIgnoreCase(request.code());

        if (hasCodeUpdated && countryRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("Country with (%s) already exists", request.code())
            );
        }

        entity.setCountryCode(request.code());
        entity.setCountryName(request.name());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        countryRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long countryId) {
        ValidationUtils.validateId(countryId);
        if (0 == countryRepository.deleteCountryById(countryId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY);
        }
    }
}
