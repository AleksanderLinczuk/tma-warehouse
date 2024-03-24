package com.smartbusiness.tmawarehouse.service;

import com.smartbusiness.tmawarehouse.model.entity.ItemEntity;
import com.smartbusiness.tmawarehouse.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public void deleteItem(ItemEntity itemEntity){
        itemRepository.delete(itemEntity);
    }

    public void saveItem(ItemEntity itemEntity){
        if (itemEntity == null){
            return;
        }else{
            itemRepository.save(itemEntity);
        }
    }
}
