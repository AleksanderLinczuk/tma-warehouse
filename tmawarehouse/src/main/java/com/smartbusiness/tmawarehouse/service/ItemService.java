package com.smartbusiness.tmawarehouse.service;

import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.model.entity.RequestEntity;
import com.smartbusiness.tmawarehouse.model.entity.UnitOfMeasurement;
import com.smartbusiness.tmawarehouse.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<ItemEntity> findAllItems(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return itemRepository.findAll();
        }else{
            return itemRepository.search(filterText);
        }
    }

    public ItemEntity findById(Long id){
        return itemRepository.findById(id).get();
    }


    public ItemEntity updateQuantity(RequestEntity request, int quantity) {
        Long itemId = request.getItemEntity().getItemId();
        Optional<ItemEntity> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ItemEntity item = optionalItem.get();
            item.setQuantity(calculateQuantity(request));
            return itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item with id " + itemId + " not found");
        }
    }

    public boolean canUpdateQuantity(RequestEntity request) {
        int requestQuantity = request.getQuantity();
        int itemQuantity = request.getItemEntity().getQuantity();
        if(request.getUnitOfMeasurement()!=request.getItemEntity().getUnitOfMeasurement()){
            if(request.getItemEntity().getUnitOfMeasurement()== UnitOfMeasurement.KILOGRAMS){
                requestQuantity/=1000;
            }else{
                requestQuantity*=1000;
            }
        }
        if (itemQuantity>= requestQuantity){
            return true;
        }
        return false;
    }

    private int calculateQuantity(RequestEntity request) {
        int requestQuantity = request.getQuantity();
        int itemQuantity = request.getItemEntity().getQuantity();
        if(request.getUnitOfMeasurement()!=request.getItemEntity().getUnitOfMeasurement()){
            if(request.getItemEntity().getUnitOfMeasurement()== UnitOfMeasurement.KILOGRAMS){
                requestQuantity/=1000;
            }else{
                requestQuantity*=1000;
            }
        }
        return itemQuantity-requestQuantity;
    }

    public long countItems(){
        return itemRepository.count();
    }


    public void deleteItem(ItemEntity itemEntity){
        itemRepository.delete(itemEntity);
    }

    public void saveItem(ItemEntity itemEntity){
        if (itemEntity == null){

            System.err.println("Item is null.");
            return;
        }else{
            itemRepository.save(itemEntity);
        }
    }

    public List<ItemEntity> findAllItems(){
        return itemRepository.findAll();
    }



}
