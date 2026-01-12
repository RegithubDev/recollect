package com.resustainability.recollect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBioWasteCategoryRequest;
import com.resustainability.recollect.dto.request.UpdateBioWasteCategoryRequest;
import com.resustainability.recollect.dto.response.IBioWasteCategoryResponse;
import com.resustainability.recollect.entity.backend.BioWasteCategory;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BioWasteCategoryRepository;

@Service
public class BioWasteCategoryService {

    private static final String FOLDER = "biowaste-category";

    private final BioWasteCategoryRepository repository;
    private final UploadService uploadService;

    public BioWasteCategoryService(
            BioWasteCategoryRepository repository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.uploadService = uploadService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBioWasteCategoryResponse> list(SearchCriteria criteria) {
        return Pager.of(
                repository.findAllPaged(
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IBioWasteCategoryResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByBioWasteCategoryId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bio waste category not found"));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddBioWasteCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        BioWasteCategory entity = new BioWasteCategory(
                null,
                request.categoryName(),
                null,
                true
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBioWasteCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        BioWasteCategory entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bio waste category not found"));

        entity.setCategoryName(request.categoryName());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String uploadImage(Long id, MultipartFile file) {
        ValidationUtils.validateId(id);
        ValidationUtils.validateMultipartSize(file, Default.MAX_IMAGE_FILE_SIZE);

        String path = uploadService.upload(FOLDER, file);

        if (repository.updateImageById(id, path) == 0) {
            uploadService.remove(path);
            throw new ResourceNotFoundException(Default.ERROR_UNABLE_TO_UPLOAD);
        }

        return path;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String removeImage(Long id) {
        ValidationUtils.validateId(id);

        BioWasteCategory entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bio waste category not found"));

        String oldImage = entity.getImage();

        if (oldImage != null) {
            uploadService.remove(oldImage);
        }

        repository.updateImageById(id, null);
        return oldImage;
    }
}
