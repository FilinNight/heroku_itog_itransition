package com.example.collectionsApp.helperСlasses.search;

import com.example.collectionsApp.helperСlasses.comparators.ComparatorSortByDataCollections;
import com.example.collectionsApp.helperСlasses.comparators.ComparatorSortByDataItems;
import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.models.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    public static List<Item> searchItems(String key, List<Item> itemsDB){
        List<Item> items = new ArrayList<>();

        int search_name;
        int search_nameCollection;
        int search_description;

        for(Item item : itemsDB){
            search_name = simpleTextSearch(key, item.getName());
            search_description = simpleTextSearch(key, item.getDescription());
            search_nameCollection = simpleTextSearch(key, item.getCollection().getName());
            if( search_name != -1 || search_description != -1 || search_nameCollection != -1){
                items.add(item);
            }
        }
        Collections.sort(items, new ComparatorSortByDataItems());
        return items;
    }

    public static List<CollectionItem> searchCollections(String key, List<CollectionItem> collectionsDB){
        List<CollectionItem> collections = new ArrayList<>();

        int search_name;
        int search_description;

        for(CollectionItem collection : collectionsDB){
            search_name = simpleTextSearch(key, collection.getName());
            search_description = simpleTextSearch(key, collection.getDescription());
            if( search_name != -1 || search_description != -1){
                collections.add(collection);
            }
        }
        Collections.sort(collections, new ComparatorSortByDataCollections());
        return collections;
    }

    private static int simpleTextSearch(String key, String txt) {

        key = key.replaceAll("\\s+","");
        key = key.toLowerCase();
        txt = txt.replaceAll("\\s+","");
        txt = txt.toLowerCase();

        char[] pattern = key.toCharArray();
        char[] text = txt.toCharArray();

        int patternSize = pattern.length;
        int textSize = text.length;

        int i = 0;

        while ((i + patternSize) <= textSize) {
            int j = 0;
            while (text[i + j] == pattern[j]) {
                j += 1;
                if (j >= patternSize)
                    return i;
            }
            i += 1;
        }
        return -1;
    }
}
