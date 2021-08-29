package com.example.collectionsApp.helperСlasses.comparators;

import com.example.collectionsApp.models.CollectionItem;
import java.util.Comparator;

public class ComparatorCollectionsByAlphabet implements Comparator<CollectionItem> {

    @Override
    public int compare(CollectionItem o1, CollectionItem o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
