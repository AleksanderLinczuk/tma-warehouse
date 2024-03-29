package com.smartbusiness.tmawarehouse.repository;

import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository <ItemEntity, Long> {


    @Query(" SELECT i FROM ItemEntity i  WHERE LOWER(i.itemGroup) LIKE LOWER (concat('%', :searchTerm, '%'))")
    List<ItemEntity> search (String searchTerm);

}
