package com.example.collectionsApp.service;

import com.example.collectionsApp.helperСlasses.search.Search;
import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.models.Item;
import com.example.collectionsApp.helperСlasses.comparators.ComparatorSortByDataItems;
import com.example.collectionsApp.repositories.ItemRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private static final Logger log = Logger.getLogger(ItemService.class);

    public boolean add(Item item, CollectionItem collection){
        try {
            item.setCollection(collection);
            item.setDate(new Date());
            if(item.getUrl() == null)
                item.setUrl("https://socialvk.ru/wp-content/uploads/avatarka-pustaya-vk_21.jpg");
            itemRepository.save(item);
            log.info("add item: " + item.getName() + ", collection: " + collection.getName() + ", user: " + collection.getUser().getUsername());
            return true;
        }catch (Exception e){
            log.error("add item: " + item.getName() + ", collection: " + collection.getName() + ", user: " + collection.getUser().getUsername());
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean changeItem(Item item){

        Item itemDB = findById(item.getId());
        try {
            if(itemDB != null){
                itemRepository.save(item);
                log.info("change item: " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
                return true;
            }
            log.debug("change item: (item does not exist) " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
            return false;
        }
        catch (Exception e){
            log.error("change item: " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(Long id){

        Item itemDB = findById(id);
        try {
            if (itemDB != null) {
                itemRepository.deleteById(id);
                log.info("delete item: " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
                return true;
            }
            log.debug("delete item: (item does not exist) " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
            return false;
        }
        catch (Exception e) {
            log.error("delete item: " + itemDB.getName() + ", collection: " + itemDB.getCollection().getName() + ", user: " + itemDB.getCollection().getUser().getUsername());
            log.error(e.getMessage());
            return false;
        }

    }

    public Item findById(Long id){
        Optional<Item> userOptional = itemRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Item> findAllByCollectionId(Long id){
        return itemRepository.findByCollection_id(id);
    }

    public List<Item> findAllSortDataByCollectionId(Long id){
        List<Item> items = itemRepository.findByCollection_id(id);
        Collections.sort(items, new ComparatorSortByDataItems());
        return items;
    }

    public List<Item> findAllSortByDate(){
        List<Item> items = itemRepository.findAll();
        Collections.sort(items, new ComparatorSortByDataItems());
        return items;
    }


    public List<Item> searchAllItems(String key){
        List<Item> items =  new ArrayList<>();
        if(key.length() != 0)
            items = Search.searchItems(key, findAll());
        return items;
    }

    public List<Item> searchAllByIdCollection(String key, Long collection_id){
        List<Item> items =  new ArrayList<>();
        if(key.length() != 0)
            items = Search.searchItems(key, findAllByCollectionId(collection_id));
        return items;
    }




}
