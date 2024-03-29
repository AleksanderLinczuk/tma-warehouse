package com.smartbusiness.tmawarehouse.service;

import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import com.smartbusiness.tmawarehouse.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public List<RequestEntity> findAllRequests(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return requestRepository.findAll();
        } else {
            return requestRepository.search(filterText);
        }
    }

    public long countRequests() {
        return requestRepository.count();
    }


    public void deleteRequest(RequestEntity requestEntity) {
        requestRepository.delete(requestEntity);
    }

    public void saveRequest(RequestEntity requestEntity) {
        if (requestEntity == null) {

            System.err.println("Request is null.");
            return;
        } else {
            requestRepository.save(requestEntity);
        }
    }

    public RequestEntity updateRequestStatus(RequestEntity request, String text) {
        Long requestId = request.getRequestId();
        Optional<RequestEntity> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            RequestEntity foundRequest = optionalRequest.get();
            foundRequest.setStatus(text);
            return requestRepository.save(foundRequest);
        } else {
            throw new EntityNotFoundException("Request with id " + requestId + " not found");
        }
    }


    public List<RequestEntity> findAllRequests() {
        return requestRepository.findAll();
    }

}
