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
import com.resustainability.recollect.dto.request.AddBioWasteTypeRequest;
import com.resustainability.recollect.dto.request.UpdateBioWasteTypeRequest;
import com.resustainability.recollect.dto.response.IBioWasteTypeResponse;
import com.resustainability.recollect.entity.backend.BioWasteCategory;
import com.resustainability.recollect.entity.backend.BioWasteType;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BioWasteCategoryRepository;
import com.resustainability.recollect.repository.BioWasteTypeRepository;

@Service
public class BioWasteTypeService {

    private static final String FOLDER = "biowaste-type";

    private final BioWasteTypeRepository repository;
    private final BioWasteCategoryRepository categoryRepository;
    private final UploadService uploadService;

    public BioWasteTypeService(
            BioWasteTypeRepository repository,
            BioWasteCategoryRepository categoryRepository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.uploadService = uploadService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBioWasteTypeResponse> listByCategory(
            Long categoryId,
            SearchCriteria criteria
    ) {
        ValidationUtils.validateId(categoryId);

        return Pager.of(
                repository.findAllPagedByCategory(
                        categoryId,
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IBioWasteTypeResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByBioWasteTypeId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BioWaste type not found"));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddBioWasteTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        BioWasteCategory category = categoryRepository.findById(request.biowasteCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("BioWaste category not found"));

        BioWasteType entity = new BioWasteType(
                null,
                request.biowasteName(),
                null,
                true,
                category
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBioWasteTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        BioWasteType entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("BioWaste type not found"));

        entity.setBiowasteName(request.biowasteName());
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

        BioWasteType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("BioWaste type not found"));

        String oldImage = entity.getImage();

        if (oldImage != null) {
            uploadService.remove(oldImage);
        }

        repository.updateImageById(id, null);
        return oldImage;
    }
}
