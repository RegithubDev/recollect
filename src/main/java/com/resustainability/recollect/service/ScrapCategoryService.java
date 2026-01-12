package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddScrapCategoryRequest;
import com.resustainability.recollect.dto.request.UpdateScrapCategoryRequest;
import com.resustainability.recollect.dto.response.IScrapCategoryResponse;
import com.resustainability.recollect.entity.backend.ScrapCategory;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ScrapCategoryRepository;
import com.resustainability.recollect.tag.OrderType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ScrapCategoryService {

    private static final String FOLDER = "scrap-category";

    private final ScrapCategoryRepository repository;
    private final UploadService uploadService;

    public ScrapCategoryService(
            ScrapCategoryRepository repository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.uploadService = uploadService;
    }

   /* @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IScrapCategoryResponse> list(SearchCriteria criteria) {
        return Pager.of(
                repository.findAllPaged(
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }*/
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IScrapCategoryResponse> listScrap(SearchCriteria criteria) {
        return Pager.of(
                repository.findAllPagedBySubcategory(
                        OrderType.SCRAP.getAbbreviation(), 
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IScrapCategoryResponse> listDisposal(SearchCriteria criteria) {
        return Pager.of(
                repository.findAllPagedBySubcategory(
                        OrderType.DISPOSALS.getAbbreviation(), 
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IScrapCategoryResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return repository.findByScrapCategoryId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Category not found"));
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long addScrapCategory(AddScrapCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        ScrapCategory entity = new ScrapCategory(
                null,
                request.categoryName(),
                "Scrap",        
                null,
                true,
                "123456"
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long addDisposalCategory(AddScrapCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        ScrapCategory entity = new ScrapCategory(
                null,
                request.categoryName(),
                "Disposals",     
                null,
                true,
                "123456"
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateScrapCategoryRequest request) {
        ValidationUtils.validateRequestBody(request);

        ScrapCategory entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap category not found"));

        entity.setCategoryName(request.categoryName());
        entity.setSubcategoryName(request.subcategoryName());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));
        entity.setHsnCode("123456");

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

        ScrapCategory entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Scrap category not found"));

        String oldImage = entity.getImage();

        if (oldImage != null) {
            uploadService.remove(oldImage);
        }

        repository.updateImageById(id, null);
        return oldImage;
    }  
    

    
}
