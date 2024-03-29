package com.smartbusiness.tmawarehouse.service;

import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import com.smartbusiness.tmawarehouse.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<RequestEntity> findAllRequests() {
        return requestRepository.findAll();
    }

}
