package com.example.collectionsApp.service;

import com.example.collectionsApp.helperСlasses.search.Search;
import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.helperСlasses.comparators.ComparatorCollectionTop;

import com.example.collectionsApp.helperСlasses.comparators.ComparatorCollectionsByAlphabet;
import com.example.collectionsApp.models.Item;
import com.example.collectionsApp.repositories.CollectionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UserService userService;


    private static final Logger log = Logger.getLogger(CollectionService.class);

    public boolean add(CollectionItem collection, String nameUser){
        try {
            collection.setUser(userService.findByName(nameUser));
            collection.setDate(new Date());
            if(collection.getUrl() == null)
                collection.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiKeMxWylWa9X7J859YdKx5r6XE1q45o7-jmnZ9p5xhNMRwrk6qICM0FZO8u7JOnR-F3M&usqp=CAU");
            collectionRepository.save(collection);
            log.info("add collection: " + collection.getName() + ", user: " + nameUser);
            return true;
        }
        catch (Exception e){
            log.error("add collection: " + collection.getName() + ", user: " + nameUser);
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean changeCollection(CollectionItem collection){
        CollectionItem collectionDB = findById(collection.getId());
        try {
            if(collectionDB != null){
                collectionRepository.save(collection);
                log.info("change collection: " + collectionDB.getName() + ", user: " + collectionDB.getUser().getUsername());
                return true;
            }
            else {
                log.debug("change collection: (collection does not exist) " + collectionDB.getName() + ", user: " + collectionDB.getUser().getUsername());
                return false;
            }
        }
        catch (Exception e){
            log.error("change collection: " + collectionDB.getName() + ", user: " + collectionDB.getUser().getUsername());
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteById(Long id){
        CollectionItem collectionFromDb = findById(id);
        try {
            if (collectionFromDb != null) {
                collectionRepository.deleteById(id);
                log.info("delete collection: " + collectionFromDb.getName() + ", user: " + collectionFromDb.getUser().getUsername());
                return true;
            }
            else {
                log.debug("delete collection: (collection does not exist) " + collectionFromDb.getName() + ", user: " + collectionFromDb.getUser().getUsername());
                return false;
            }

        }catch (Exception e){
            log.error("delete collection: " + collectionFromDb.getName() + ", user: " + collectionFromDb.getUser().getUsername());
            log.error(e.getMessage());
            return false;
        }
    }

    public List<CollectionItem> findAll() {
        return collectionRepository.findAll();
    }

    public List<CollectionItem> findAllSort() {
        List<CollectionItem> collections = collectionRepository.findAll();
        Collections.sort(collections, new ComparatorCollectionsByAlphabet());
        return collections;
    }

    public List<CollectionItem> findTop() {
        List<CollectionItem> collections = collectionRepository.findAll();
        List<CollectionItem> sortCollections = new ArrayList<>();
        Comparator comparator = new ComparatorCollectionTop();

        for (int i = 0; i < collections.size(); i++) {
            if(collections.get(i).sizeItems() != 0) {
                sortCollections.add(collections.get(i));
            }
        }

        Collections.sort(sortCollections, comparator);

        if(sortCollections.size() > 10)
            sortCollections.subList(10, sortCollections.size()).clear();

        return sortCollections;

    }

    public List<CollectionItem> findAllByUserId(long id) {
        return collectionRepository.findByUser_id(id);
    }

    public List<CollectionItem> findAllSortByUserId(long id) {
        List<CollectionItem> collections = collectionRepository.findByUser_id(id);
        Collections.sort(collections, new ComparatorCollectionsByAlphabet());
        return collections;
    }

    public CollectionItem findById(Long id){
        Optional<CollectionItem> collectionOptional = collectionRepository.findById((long) id);
        return collectionOptional.orElse(null);
    }

    public int sizeAllItems(){
        int size = 0;
        List<CollectionItem> collections = collectionRepository.findAll();
        for(CollectionItem collection : collections){
            size += collection.sizeItems();
        }
        return size;
    }

    public int sizeAllCollections(){
        List<CollectionItem> collections = collectionRepository.findAll();
        return collections.size();
    }

    public List<CollectionItem> searchAllCollections(String key){
        List<CollectionItem> collections =  new ArrayList<>();
        if(key.length() != 0)
            collections = Search.searchCollections(key, findAll());
        return collections;
    }


}
