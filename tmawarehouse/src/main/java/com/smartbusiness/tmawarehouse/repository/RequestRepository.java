package com.smartbusiness.tmawarehouse.repository;

import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {



    @Query(" SELECT r FROM RequestEntity r  WHERE (r.requestId) = (:searchTerm)")
    List<RequestEntity> search (String searchTerm);

}