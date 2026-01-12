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
import com.resustainability.recollect.dto.request.AddScrapTypeRequest;
import com.resustainability.recollect.dto.request.UpdateScrapTypeRequest;
import com.resustainability.recollect.dto.response.IScrapTypeResponse;
import com.resustainability.recollect.entity.backend.ScrapCategory;
import com.resustainability.recollect.entity.backend.ScrapType;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ScrapCategoryRepository;
import com.resustainability.recollect.repository.ScrapTypeRepository;

@Service
public class ScrapTypeService {

    private static final String FOLDER = "scrap-type";

    private final ScrapTypeRepository repository;
    private final ScrapCategoryRepository categoryRepository;
    private final UploadService uploadService;

    public ScrapTypeService(
            ScrapTypeRepository repository,
            ScrapCategoryRepository categoryRepository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.uploadService = uploadService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IScrapTypeResponse> listByCategory(
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
    public IScrapTypeResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByScrapTypeId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap type not found"));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddScrapTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        ScrapCategory category = categoryRepository.findById(request.scrapCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap category not found"));

        ScrapType entity = new ScrapType(
                null,
                request.scrapName(),
                null,
                request.isPayable(),
                request.isKg(),
                true,
                category
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateScrapTypeRequest request) {
        ValidationUtils.validateRequestBody(request);

        ScrapType entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap type not found"));

        entity.setScrapName(request.scrapName());
        entity.setPayable(request.isPayable());
        entity.setKg(request.isKg());
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

        ScrapType entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap type not found"));

        String oldImage = entity.getImage();

        if (oldImage != null) {
            uploadService.remove(oldImage);
        }

        repository.updateImageById(id, null);
        return oldImage;
    }
}
