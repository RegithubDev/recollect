package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IProviderDistrictResponse;
import com.resustainability.recollect.dto.response.IProviderResponse;
import com.resustainability.recollect.dto.response.ProviderCashCollectionResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.repository.ProviderDistrictRepository;
import com.resustainability.recollect.repository.ProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderDistrictRepository providerDistrictRepository;

    @Autowired
    public ProviderService(
            ProviderRepository providerRepository,
            ProviderDistrictRepository providerDistrictRepository
    ) {
        this.providerRepository = providerRepository;
        this.providerDistrictRepository = providerDistrictRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Optional<Provider> findByUsernameAndPassword(String username, String password) {
        ValidationUtils.validateUsername(username);
        ValidationUtils.validatePassword(password);
        return providerRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshLastLoginAtById(Long providerId) {
        ValidationUtils.validateUserId(providerId);
        return providerRepository.updateLastLoginAtById(providerId, LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshTokenAtById(Long providerId) {
        ValidationUtils.validateUserId(providerId);
        return providerRepository.updateTokenAtById(providerId, LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ProviderCashCollectionResponse> listCashCollection(SearchCriteria searchCriteria) {
        final Pageable pageRequest = searchCriteria.toPageRequest();

        final Page<IProviderResponse> paged = providerRepository
                .findAllCashCollectionPaged(
                        searchCriteria.getQ(),
                        pageRequest
                );

        if (paged.isEmpty()) {
            return Pager.empty(pageRequest);
        }

        final Map<Long, Set<String>> indexedDistricts = providerDistrictRepository
                .listAllActiveProviderDistricts(
                        paged.stream()
                                .map(IProviderResponse::getId)
                                .collect(Collectors.toSet())
                )
                .stream()
                .collect(Collectors.groupingBy(
                        IProviderDistrictResponse::getProviderId,
                        Collectors.mapping(
                                IProviderDistrictResponse::getDistrictName,
                                Collectors.toSet()
                        )
                ));

        return Pager.of(
                paged.map(projection ->
                        new ProviderCashCollectionResponse(
                            projection.getId(),
                            projection.getCode(),
                            projection.getFullName(),
                            projection.getScrapCashInHand(),
                            projection.getBioCashInHand(),
                            projection.getBwgScrapCashInHand(),
                            projection.getBwgBioCashInHand(),
                            projection.getTotalCashInHand(),
                            indexedDistricts.getOrDefault(
                                    projection.getId(),
                                    Collections.emptySet()
                            )
                        )
                )
        );
    }
}
