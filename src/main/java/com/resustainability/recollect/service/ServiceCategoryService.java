package com.resustainability.recollect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddServiceCategoryRequest;
import com.resustainability.recollect.dto.request.UpdateServiceCategoryRequest;
import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.entity.backend.ServiceCategory;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ServiceCategoryRepository;



@Service
public class ServiceCategoryService {

    private static final String FOLDER = "service-category";

    private final ServiceCategoryRepository repository;
    private final UploadService uploadService;

    @Autowired
    public ServiceCategoryService(
            ServiceCategoryRepository repository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.uploadService = uploadService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IServiceCategoryResponse> list(SearchCriteria criteria) {
        return Pager.of(
                repository.findAllPaged(
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IServiceCategoryResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return repository.findByServiceCategoryId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Category not found"));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddServiceCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repository.existsByServiceNameIgnoreCase(request.name())) {
            throw new DataAlreadyExistException("Service category already exists");
        }

        return repository.save(
                new ServiceCategory(
                        null,
                        request.name(),
                        request.title(),
                        request.subtitle(),
                        null,
                        true,
                        false,
                        request.categoryUrl(),
                        request.orderUrl(),
                        request.scheduleUrl()
                )
        ).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateServiceCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        ServiceCategory entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Service Category not found"));

        entity.setServiceName(request.name());
        entity.setServiceTitle(request.title());
        entity.setServiceSubtitle(request.subtitle());
        entity.setCategoryUrl(request.categoryUrl());
        entity.setOrderUrl(request.orderUrl());
        entity.setScheduleUrl(request.scheduleUrl());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void delete(Long id, boolean delete) {
        ValidationUtils.validateId(id);
        if (repository.softDelete(id, !delete, delete) == 0) {
            throw new ResourceNotFoundException("Service Category not found");
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String uploadIcon(Long id, MultipartFile file) {
        ValidationUtils.validateId(id);
        ValidationUtils.validateMultipartSize(file, Default.MAX_IMAGE_FILE_SIZE);

        String path = uploadService.upload(FOLDER, file);

        if (repository.updateIconById(id, path) == 0) {
            uploadService.remove(path);
            throw new ResourceNotFoundException(Default.ERROR_UNABLE_TO_UPLOAD);
        }

        return path;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String removeIcon(Long id) {
        ValidationUtils.validateId(id);

        ServiceCategory entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service Category not found"));

        String oldIcon = entity.getIcon();

        if (oldIcon != null) {
            uploadService.remove(oldIcon);
        }

        repository.updateIconById(id, null);

        return oldIcon;
    }

}
