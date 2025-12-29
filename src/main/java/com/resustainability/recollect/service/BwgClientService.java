package com.resustainability.recollect.service;

import java.time.LocalDateTime;

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
import com.resustainability.recollect.dto.request.AddBwgClientRequest;
import com.resustainability.recollect.dto.request.UpdateBwgClientRequest;
import com.resustainability.recollect.dto.response.IBwgClientResponse;
import com.resustainability.recollect.entity.backend.BwgClient;
import com.resustainability.recollect.entity.backend.District;
import com.resustainability.recollect.entity.backend.ScrapRegion;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.entity.backend.Ward;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgClientRepository;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;
import com.resustainability.recollect.repository.StateRepository;
import com.resustainability.recollect.repository.WardRepository;

@Service
public class BwgClientService {

    private static final String FOLDER_NAME = "client-contract";

    private final UploadService uploadService;
    private final BwgClientRepository repository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final ScrapRegionRepository scrapRegionRepository;

    @Autowired
    public BwgClientService(UploadService uploadService, BwgClientRepository repository,StateRepository stateRepository,DistrictRepository districtRepository,
    		WardRepository wardRepository,ScrapRegionRepository scrapRegionRepository) {
        this.uploadService = uploadService;
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
        this.scrapRegionRepository = scrapRegionRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBwgClientResponse> list(SearchCriteria criteria) {
        return Pager.of(repository.findAllPaged(
                criteria.getQ(),
                criteria.toPageRequest()
        ));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IBwgClientResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByClientId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_BWG_CLIENT));
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddBwgClientRequest request) {

        ValidationUtils.validateRequestBody(request);

        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        Ward ward = null;
        if (request.wardId() != null) {
            ward = wardRepository.findById(request.wardId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));
        }

        ScrapRegion scrapRegion = null;
        if (request.scrapRegionId() != null) {
            scrapRegion = scrapRegionRepository.findById(request.scrapRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));
        }

        BwgClient client = new BwgClient();

       
        client.setFullName(request.fullName());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());
        client.setAlternateNumber(request.alternateNumber());

        
        client.setState(state);
        client.setDistrict(district);
        client.setWard(ward);
        client.setScrapRegion(scrapRegion);

       
        client.setServiceType(request.serviceType());
        client.setClientCategory(request.clientCategory());
        client.setFamilyNumber(request.familyNumber());
        client.setVerificationStatus(request.verificationStatus());

       
        client.setMonthlyContract(request.monthlyContract());
        client.setClientPrice(request.clientPrice());
        client.setClientCgst(request.clientCgst());
        client.setClientSgst(request.clientSgst());

        
        client.setGstName(request.gstName());
        client.setGstNo(request.gstNo());
        client.setAccountNumber(request.accountNumber());
        client.setIfscCode(request.ifscCode());

        
        client.setUsername(request.username());
        client.setPassword(request.password());
        client.setPlainPassword(request.password());

       
        client.setContractStartDate(request.contractStartDate());
        client.setContractEndDate(request.contractEndDate());
        client.setCollectionFrequency(request.collectionFrequency());

        
        client.setRemark(request.remark());
        client.setAddress(request.address());

      
        client.setDateJoined(LocalDateTime.now());
        client.setActive(true);
        client.setDeleted(false);
        client.setBioOrder(true);
        client.setScrapOrder(true);
        client.setRequestApproved(false);

        return repository.save(client).getId();
    }

    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBwgClientRequest request) {

        ValidationUtils.validateRequestBody(request);

        BwgClient client = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_BWG_CLIENT));

      
        client.setFullName(request.fullName());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());
        client.setAlternateNumber(request.alternateNumber());

      
        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        Ward ward = null;
        if (request.wardId() != null) {
            ward = wardRepository.findById(request.wardId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));
        }

        ScrapRegion scrapRegion = null;
        if (request.scrapRegionId() != null) {
            scrapRegion = scrapRegionRepository.findById(request.scrapRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));
        }

        client.setState(state);
        client.setDistrict(district);
        client.setWard(ward);
        client.setScrapRegion(scrapRegion);

      
        client.setServiceType(request.serviceType());
        client.setClientCategory(request.clientCategory());
        client.setFamilyNumber(request.familyNumber());
        client.setVerificationStatus(request.verificationStatus());

       
        client.setMonthlyContract(request.monthlyContract());
        client.setClientPrice(request.clientPrice());
        client.setClientCgst(request.clientCgst());
        client.setClientSgst(request.clientSgst());

       
        client.setGstName(request.gstName());
        client.setGstNo(request.gstNo());
        client.setAccountNumber(request.accountNumber());
        client.setIfscCode(request.ifscCode());

       
        client.setUsername(request.username());

        
        if (request.password() != null && !request.password().isBlank()) {
            client.setPassword(request.password());
            client.setPlainPassword(request.password());
        }

      
        client.setContractStartDate(request.contractStartDate());
        client.setContractEndDate(request.contractEndDate());
        client.setCollectionFrequency(request.collectionFrequency());

     
        client.setAddress(request.address());
        client.setRemark(request.remark());
        client.setActive(request.isActive());

        repository.save(client);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void delete(Long id, boolean value) {
        ValidationUtils.validateId(id);
        if (repository.deleteById(id, !value, value) == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_BWG_CLIENT);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String uploadContract(Long id, MultipartFile file) {
        ValidationUtils.validateId(id);
        ValidationUtils.validateMultipartSize(file, Default.MAX_FILE_SIZE);

        String path = uploadService.upload(FOLDER_NAME, file);

        if (repository.updateContract(id, path) == 0) {
            uploadService.remove(path);
            throw new ResourceNotFoundException("Unable to upload");
        }
        return path;
    }
    
    
 
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String removeContract(Long id) {
        ValidationUtils.validateId(id);

        String path = repository.findContractById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_BWG_CLIENT));

        uploadService.remove(path);
        repository.updateContract(id, null);

        return path;
    }


}
