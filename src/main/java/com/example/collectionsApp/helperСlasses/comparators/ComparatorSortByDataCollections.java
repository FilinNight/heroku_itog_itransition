package com.example.collectionsApp.helperСlasses.comparators;

import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.models.Item;

import java.util.Comparator;

public class ComparatorSortByDataCollections implements Comparator<CollectionItem> {

    @Override
    public int compare(CollectionItem o1, CollectionItem o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
