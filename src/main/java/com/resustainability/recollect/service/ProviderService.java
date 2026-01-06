package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderAddOrderLimitRepository;
import com.resustainability.recollect.repository.ProviderCashLimitRepository;
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
    private final ProviderCashLimitRepository providerCashLimitRepository;
    private final ProviderAddOrderLimitRepository providerAddOrderLimitRepository;

    @Autowired
    public ProviderService(
            ProviderRepository providerRepository,
            ProviderDistrictRepository providerDistrictRepository,
            ProviderCashLimitRepository providerCashLimitRepository,
            ProviderAddOrderLimitRepository providerAddOrderLimitRepository
    ) {
        this.providerRepository = providerRepository;
        this.providerDistrictRepository = providerDistrictRepository;
        this.providerCashLimitRepository = providerCashLimitRepository;
        this.providerAddOrderLimitRepository = providerAddOrderLimitRepository;
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

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ProviderResponse> list(SearchCriteria searchCriteria) {
        final Pageable pageRequest = searchCriteria.toPageRequest();

        final Page<IProviderResponse> paged = providerRepository
                .findAllPaged(
                        searchCriteria.getQ(),
                        pageRequest
                );

        if (paged.isEmpty()) {
            return Pager.empty(pageRequest);
        }

        final Set<Long> providerIds = paged
                .stream()
                .map(IProviderResponse::getId)
                .collect(Collectors.toSet());

        final Map<Long, Set<String>> indexedDistricts = providerDistrictRepository
                .listAllActiveProviderDistricts(providerIds)
                .stream()
                .collect(Collectors.groupingBy(
                        IProviderDistrictResponse::getProviderId,
                        Collectors.mapping(
                                IProviderDistrictResponse::getDistrictName,
                                Collectors.toSet()
                        )
                ));

        final Map<Long, Integer> indexedCashLimit = providerCashLimitRepository
                .listAllProvidersCashLimit(providerIds)
                .stream()
                .collect(Collectors.toMap(
                        IProviderCashLimitResponse::getProviderId,
                        IProviderCashLimitResponse::getCashLimit,
                        (existing, replacement) -> replacement
                ));

        final Map<Long, Integer> indexedAddOrderLimit = providerAddOrderLimitRepository
                .listAllProvidersAddOrderLimit(providerIds)
                .stream()
                .collect(Collectors.toMap(
                        IProviderAddOrderLimitResponse::getProviderId,
                        IProviderAddOrderLimitResponse::getCurrentLimit,
                        (existing, replacement) -> replacement
                ));

        return Pager.of(
                paged.map(projection ->
                        new ProviderResponse(
                                projection.getId(),
                                projection.getCode(),
                                projection.getFullName(),
                                projection.getPhoneNumber(),
                                projection.getStateId(),
                                projection.getStateName(),
                                projection.getStateCode(),
                                "Service Provider",
                                projection.getPassword(),
                                Boolean.TRUE.equals(projection.getScrapPickup()),
                                Boolean.TRUE.equals(projection.getBiowastePickup()),
                                Boolean.TRUE.equals(projection.getBwgBioPickup()),
                                Boolean.TRUE.equals(projection.getBwgScrapPickup()),
                                Boolean.TRUE.equals(projection.getIsActive()),
                                projection.getOrderPickupLimit(),
                                indexedCashLimit.getOrDefault(
                                        projection.getId(),
                                        0
                                ),
                                indexedAddOrderLimit.getOrDefault(
                                        projection.getId(),
                                        0
                                ),
                                0,
                                indexedDistricts.getOrDefault(
                                        projection.getId(),
                                        Collections.emptySet()
                                )
                        )
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ProviderResponse getById(Long providerId) {
        ValidationUtils.validateId(providerId);

        final IProviderResponse provider = providerRepository
                .findProviderById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        final Set<String> providerDistricts = providerDistrictRepository
                .listAllActiveProviderDistrictsById(providerId)
                .stream()
                .map(IProviderDistrictResponse::getDistrictName)
                .collect(Collectors.toSet());

        final Integer providerCashLimit = providerCashLimitRepository
                .listAllProviderCashLimitById(providerId)
                .stream()
                .findFirst()
                .map(IProviderCashLimitResponse::getCashLimit)
                .orElse(0);

        final Integer providerAddOrderLimit = providerAddOrderLimitRepository
                .listAllProviderAddOrderLimitById(providerId)
                .stream()
                .findFirst()
                .map(IProviderAddOrderLimitResponse::getCurrentLimit)
                .orElse(0);

        return new ProviderResponse(
                provider.getId(),
                provider.getCode(),
                provider.getFullName(),
                provider.getPhoneNumber(),
                provider.getStateId(),
                provider.getStateName(),
                provider.getStateCode(),
                "Service Provider",
                provider.getPassword(),
                Boolean.TRUE.equals(provider.getScrapPickup()),
                Boolean.TRUE.equals(provider.getBiowastePickup()),
                Boolean.TRUE.equals(provider.getBwgBioPickup()),
                Boolean.TRUE.equals(provider.getBwgScrapPickup()),
                Boolean.TRUE.equals(provider.getIsActive()),
                provider.getOrderPickupLimit(),
                providerCashLimit,
                providerAddOrderLimit,
                0,
                providerDistricts
        );
    }
}
