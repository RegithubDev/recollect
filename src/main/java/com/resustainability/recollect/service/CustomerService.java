package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerProfileRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerRequest;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final SecurityService securityService;
    private final CustomerRepository customerRepository;
    private final DistrictRepository districtRepository;
    private final ScrapRegionRepository scrapRegionRepository;
    private final StateRepository stateRepository;
    private final WardRepository wardRepository;

    @Autowired
    public CustomerService(
            SecurityService securityService,
            CustomerRepository customerRepository,
            DistrictRepository districtRepository,
            ScrapRegionRepository scrapRegionRepository,
            StateRepository stateRepository,
            WardRepository wardRepository
    ) {
        this.securityService = securityService;
        this.customerRepository = customerRepository;
        this.districtRepository = districtRepository;
        this.scrapRegionRepository = scrapRegionRepository;
        this.stateRepository = stateRepository;
        this.wardRepository = wardRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        ValidationUtils.validatePhone(phoneNumber);
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshLastLoginAtById(Long customerId) {
        ValidationUtils.validateUserId(customerId);
        return customerRepository.updateLastLoginAtById(customerId, LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshTokenAtById(Long customerId) {
        ValidationUtils.validateUserId(customerId);
        return customerRepository.updateTokenAtById(customerId, LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICustomerResponse> listCustomers(SearchCriteria searchCriteria) {
        return Pager.of(
                customerRepository.findAllPaged(
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ICustomerResponse getById(Long customerId) {
        ValidationUtils.validateUserId(customerId);
        return customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void add(AddCustomerRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DataAlreadyExistException(
                    String.format("Customer with (%s) already exists", request.phoneNumber())
            );
        }

        if (null != request.districtId() && !districtRepository.existsById(request.districtId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT);
        }

        if (null != request.scrapRegionId() && !scrapRegionRepository.existsById(request.scrapRegionId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }

        if (null != request.stateId() && !stateRepository.existsById(request.stateId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE);
        }

        if (null != request.wardId() && !wardRepository.existsById(request.wardId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }

        customerRepository.save(
                new Customer(
                        null,
                        Default.EMPTY,
                        null,
                        Boolean.TRUE.equals(request.isSuperuser()),
                        Boolean.TRUE.equals(request.isStaff()),
                        true,
                        request.dateJoined(),
                        request.name(),
                        null,
                        request.phoneNumber(),
                        request.email(),
                        request.userType(),
                        request.platform(),
                        false,
                        null != request.districtId() ? districtRepository.getReferenceById(request.districtId()) : null,
                        null != request.scrapRegionId() ? scrapRegionRepository.getReferenceById(request.scrapRegionId()) : null,
                        null != request.stateId() ? stateRepository.getReferenceById(request.stateId()) : null,
                        null != request.wardId() ? wardRepository.getReferenceById(request.wardId()) : null,
                        LocalDateTime.now()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateCustomerRequest request) {
        ValidationUtils.validateRequestBody(request);

        final ICustomerResponse customer = customerRepository
                .findByCustomerId(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));

        final Customer entity = customerRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));

        final boolean hasPhoneUpdated = !Objects.equals(customer.getPhoneNumber(), request.phoneNumber());
        final boolean hasDistrictUpdated = !Objects.equals(customer.getDistrictId(), request.districtId());
        final boolean hasScrapRegionUpdated = !Objects.equals(customer.getScrapRegionId(), request.scrapRegionId());
        final boolean hasStateUpdated = !Objects.equals(customer.getStateId(), request.stateId());
        final boolean hasWardUpdated = !Objects.equals(customer.getWardId(), request.wardId());

        if (hasPhoneUpdated && customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DataAlreadyExistException(
                    String.format("Customer with (%s) already exists", request.phoneNumber())
            );
        }

        if (hasDistrictUpdated) {
            if (null == request.districtId()) {
                entity.setDistrict(null);
            } else if (!districtRepository.existsById(request.districtId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT);
            } else {
                entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
            }
        }

        if (hasScrapRegionUpdated) {
            if (null == request.scrapRegionId()) {
                entity.setScrapRegion(null);
            } else if (!scrapRegionRepository.existsById(request.scrapRegionId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
            } else {
                entity.setScrapRegion(scrapRegionRepository.getReferenceById(request.scrapRegionId()));
            }
        }

        if (hasStateUpdated) {
            if (null == request.stateId()) {
                entity.setState(null);
            } else if (!stateRepository.existsById(request.stateId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE);
            } else {
                entity.setState(stateRepository.getReferenceById(request.stateId()));
            }
        }

        if (hasWardUpdated) {
            if (null == request.wardId()) {
                entity.setWard(null);
            } else if (!wardRepository.existsById(request.wardId())) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
            } else {
                entity.setWard(wardRepository.getReferenceById(request.wardId()));
            }
        }

        entity.setFullName(request.name());
        entity.setPhoneNumber(request.phoneNumber());
        entity.setEmail(request.email());
        entity.setUserType(request.userType());
        entity.setPlatform(request.platform());
        entity.setSuperuser(Boolean.TRUE.equals(request.isSuperuser()));
        entity.setStaff(Boolean.TRUE.equals(request.isStaff()));
        entity.setActive(Boolean.TRUE.equals(request.isActive()));
        entity.setDeleted(Boolean.TRUE.equals(request.isDeleted()));
        entity.setDateJoined(request.dateJoined());

        customerRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateProfile(UpdateCustomerProfileRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Long customerId = securityService
                .getCurrentUserId()
                .orElseThrow(UnauthorizedException::new);

        final Customer entity = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));

        final boolean hasPhoneUpdated = !Objects.equals(entity.getPhoneNumber(), request.phoneNumber());

        if (hasPhoneUpdated && customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DataAlreadyExistException(
                    String.format("Customer with (%s) already exists", request.phoneNumber())
            );
        }

        entity.setFullName(request.name());
        entity.setPhoneNumber(request.phoneNumber());
        entity.setEmail(request.email());

        customerRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long customerId) {
        ValidationUtils.validateUserId(customerId);

        final Customer entity = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));

        entity.setTokenAt(LocalDateTime.now());
        entity.setActive(false);
        entity.setDeleted(true);

        if (StringUtils.isNotBlank(entity.getEmail())) {
            entity.setEmail(
                    Default.DELETED_PREFIX
                            + entity.getEmail()
                            + Default.UNDERSCORE
                            + entity.getId()
            );
        }

        if (StringUtils.isNotBlank(entity.getPhoneNumber())) {
            entity.setPhoneNumber(
                    Default.DELETED_PREFIX
                            + entity.getPhoneNumber()
                            + Default.UNDERSCORE
                            + entity.getId()
            );
        }

        customerRepository.save(entity);
    }
}
