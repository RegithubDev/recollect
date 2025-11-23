package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddLocalBodyTypeRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyTypeRequest;
import com.resustainability.recollect.dto.response.ILocalBodyTypeResponse;
import com.resustainability.recollect.entity.backend.LocalBodyType;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.LocalBodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
public class LocalBodyTypeService {

    private final LocalBodyTypeRepository repository;

    @Autowired
    public LocalBodyTypeService(LocalBodyTypeRepository repository) {
        this.repository = repository;
    }

    public Pager<ILocalBodyTypeResponse> list(SearchCriteria searchCriteria) {
        return Pager.of(repository.findAllPaged(
                searchCriteria.getQ(),
                searchCriteria.toPageRequest()
        ));
    }

    @Transactional
    public ILocalBodyTypeResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository
                .findByLocalBodyTypeId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCALBODYTYPE));
    }

    @Transactional
    public Long add(AddLocalBodyTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repository.existsByName(request.name())) {
            throw new DataAlreadyExistException(
                    String.format("Local Body Type (%s) already exists", request.name())
            );
        }

        LocalBodyType entity = new LocalBodyType();
        entity.setLocalBodyType(request.name());
        entity.setActive(true);
        entity.setDeleted(false);

        LocalBodyType saved = repository.save(entity);
        return saved.getId();
    }

    @Transactional
    public void update(UpdateLocalBodyTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        LocalBodyType entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCALBODYTYPE));

        boolean nameUpdated = !entity.getLocalBodyType().equalsIgnoreCase(request.name());

        if (nameUpdated && repository.existsByName(request.name())) {
            throw new DataAlreadyExistException(
                    String.format("Local Body Type (%s) already exists", request.name())
            );
        }

        entity.setLocalBodyType(request.name());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        repository.save(entity);
    }

    @Transactional
    public void deleteById(Long id, boolean value) {
        ValidationUtils.validateId(id);

        if (0 == repository.deleteLocalBodyTypeById(id, !value, value)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCALBODYTYPE);
        }
    }
}
