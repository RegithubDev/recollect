package com.resustainability.recollect.service;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderTeamAllotedWardRequest;
import com.resustainability.recollect.dto.request.UpdateProviderTeamAllotedWardRequest;
import com.resustainability.recollect.dto.response.IProviderTeamAllotedWardResponse;
import com.resustainability.recollect.entity.backend.ProviderTeamAllotedWards;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderTeamAllotedWardsRepository;
import com.resustainability.recollect.repository.ProviderTeamRepository;
import com.resustainability.recollect.repository.WardRepository;

@Service
public class ProviderTeamAllotedWardsService {

    private final ProviderTeamAllotedWardsRepository repository;
    private final ProviderTeamRepository teamRepository;
    private final WardRepository wardRepository;

    public ProviderTeamAllotedWardsService(
            ProviderTeamAllotedWardsRepository repository,
            ProviderTeamRepository teamRepository,
            WardRepository wardRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
        this.wardRepository = wardRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IProviderTeamAllotedWardResponse> list(SearchCriteria criteria) {
        return Pager.of(repository.list(criteria.getQ(), criteria.toPageRequest()));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderTeamAllotedWardResponse getById(Long id) {
        ValidationUtils.validateId(id);
        IProviderTeamAllotedWardResponse response = repository.getDetails(id);

        if (response == null)
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);

        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderTeamAllotedWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (repository.existsByTeam_IdAndWard_Id(request.teamId(), request.wardId())) {
            throw new DataAlreadyExistException(Default.ERROR_ALREADY_ALLOTED);
        }

        var team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDERTEAM));

        var ward = wardRepository.findById(request.wardId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        ProviderTeamAllotedWards entity = new ProviderTeamAllotedWards();

        entity.setTeam(team);
        entity.setWard(ward);

        entity.setMonday(request.monday());
        entity.setTuesday(request.tuesday());
        entity.setWednesday(request.wednesday());
        entity.setThursday(request.thursday());
        entity.setFriday(request.friday());
        entity.setSaturday(request.saturday());
        entity.setSunday(request.sunday());

        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderTeamAllotedWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderTeamAllotedWards entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        if (request.monday() != null) entity.setMonday(request.monday());
        if (request.tuesday() != null) entity.setTuesday(request.tuesday());
        if (request.wednesday() != null) entity.setWednesday(request.wednesday());
        if (request.thursday() != null) entity.setThursday(request.thursday());
        if (request.friday() != null) entity.setFriday(request.friday());
        if (request.saturday() != null) entity.setSaturday(request.saturday());
        if (request.sunday() != null) entity.setSunday(request.sunday());

        repository.save(entity);
    }

}
