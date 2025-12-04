package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderTeamRequest;
import com.resustainability.recollect.dto.request.UpdateProviderTeamRequest;
import com.resustainability.recollect.dto.response.IProviderTeamResponse;
import com.resustainability.recollect.entity.backend.ProviderTeam;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderTeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;

@Service
public class ProviderTeamService {

    private final ProviderTeamRepository teamRepository;

    public ProviderTeamService(ProviderTeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Pager<IProviderTeamResponse> list(SearchCriteria criteria) {
        return Pager.of(
                teamRepository.findAllPaged(criteria.getQ(), criteria.toPageRequest())
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderTeamResponse getById(Long teamId) {
        ValidationUtils.validateId(teamId);

        IProviderTeamResponse data = teamRepository.findDetails(teamId);
        if (data == null) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDERTEAM);
        }
        return data;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderTeamRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (teamRepository.existsByTeamNameIgnoreCase(request.teamName())) {
            throw new DataAlreadyExistException(
                    String.format("Team with name (%s) already exists", request.teamName())
            );
        }

        ProviderTeam entity = new ProviderTeam();
        entity.setTeamName(request.teamName());
        entity.setIsActive(true);
        entity.setIsDeleted(false);
        entity.setCreatedAt(LocalDateTime.now());

        ProviderTeam saved = teamRepository.save(entity);
        return saved.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderTeamRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderTeam entity = teamRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDERTEAM));

        
        if (request.teamName() != null && !request.teamName().equalsIgnoreCase(entity.getTeamName())) {

            if (teamRepository.existsByTeamNameIgnoreCase(request.teamName())) {
                throw new DataAlreadyExistException(
                        String.format("Team with name (%s) already exists", request.teamName())
                );
            }

            entity.setTeamName(request.teamName());
        }

        if (request.isActive() != null) {
            entity.setIsActive(request.isActive());
        }

        teamRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long teamId, boolean value) {

        ValidationUtils.validateId(teamId);

        if (teamRepository.softDelete(teamId, !value, value) == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDERTEAM);
        }
    }
}
