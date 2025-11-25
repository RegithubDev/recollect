package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderRoleRequest;
import com.resustainability.recollect.dto.request.UpdateProviderRoleRequest;
import com.resustainability.recollect.dto.response.IProviderRoleResponse;
import com.resustainability.recollect.entity.backend.ProviderRoles;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderRoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderRoleService {

    private final ProviderRoleRepository roleRepository;

    public ProviderRoleService(ProviderRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IProviderRoleResponse> list(SearchCriteria criteria) {
        return Pager.of(
                roleRepository.findAllPaged(
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderRoleResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return roleRepository.findByProviderRoleId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_ROLE));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderRoleRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (roleRepository.existsByRoleName(request.roleName())) {
            throw new DataAlreadyExistException(
                    "Role already exists: " + request.roleName()
            );
        }

        return roleRepository.save(
                new ProviderRoles(null, request.roleName(), request.isAdmin(), true)
        ).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderRoleRequest request) {
        ValidationUtils.validateRequestBody(request);

        ProviderRoles entity = roleRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_ROLE));

        entity.setRoleName(request.roleName());
        entity.setAdmin(Boolean.TRUE.equals(request.isAdmin()));
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        roleRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteOrUndelete(Long id, boolean active) {
        ValidationUtils.validateId(id);

        if (roleRepository.updateIsActive(id, active) == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_ROLE);
        }
    }
}
