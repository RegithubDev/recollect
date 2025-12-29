package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgBagPriceRequest;
import com.resustainability.recollect.dto.request.UpdateBwgBagPriceRequest;
import com.resustainability.recollect.dto.response.IBwgBagPriceResponse;
import com.resustainability.recollect.entity.backend.BwgBagPrice;
import com.resustainability.recollect.entity.backend.BwgClient;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgBagPriceRepository;
import com.resustainability.recollect.repository.BwgClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BwgBagPriceService {

    private final BwgBagPriceRepository repository;
    private final BwgClientRepository clientRepository;

    public BwgBagPriceService(
            BwgBagPriceRepository repository,
            BwgClientRepository clientRepository
    ) {
        this.repository = repository;
        this.clientRepository = clientRepository;
    }

    public Pager<IBwgBagPriceResponse> list(Long clientId, SearchCriteria searchCriteria) {
        return Pager.of(
                repository.findAllPaged(
                        searchCriteria.getQ(),
                        clientId,
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional
    public IBwgBagPriceResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return repository.findByBagPriceId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_BWG_BAG_PRICE
                        )
                );
    }

    @Transactional
    public Long add(AddBwgBagPriceRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repository.existsByBagSizeAndClient(
                request.bagSize(), request.clientId())) {
            throw new DataAlreadyExistException(
                    String.format("Bag size (%s) already exists for this client",
                            request.bagSize())
            );
        }

        BwgClient client = clientRepository.findById(request.clientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_BWG_CLIENT
                        )
                );

        BwgBagPrice entity = new BwgBagPrice();
        entity.setBagSize(request.bagSize());
        entity.setBagPrice(request.bagPrice());
        entity.setBagCgst(request.bagCgst());
        entity.setBagSgst(request.bagSgst());
        entity.setClient(client);
        entity.setIsActive(true);

        return repository.save(entity).getId();
    }

    @Transactional
    public void update(UpdateBwgBagPriceRequest request) {
        ValidationUtils.validateRequestBody(request);

        BwgBagPrice entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_BWG_BAG_PRICE
                        )
                );

        BwgClient client = clientRepository.findById(request.clientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_BWG_CLIENT
                        )
                );

        entity.setBagSize(request.bagSize());
        entity.setBagPrice(request.bagPrice());
        entity.setBagCgst(request.bagCgst());
        entity.setBagSgst(request.bagSgst());
        entity.setIsActive(request.isActive());
        entity.setClient(client);

        repository.save(entity);
    }
}
