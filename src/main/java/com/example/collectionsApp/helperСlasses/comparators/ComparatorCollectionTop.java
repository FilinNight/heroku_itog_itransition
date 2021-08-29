package com.example.collectionsApp.helperСlasses.comparators;

import com.example.collectionsApp.models.CollectionItem;
import java.util.Comparator;

public class  ComparatorCollectionTop implements Comparator<CollectionItem> {

    @Override
    public int compare(CollectionItem o1, CollectionItem o2) {
        return o2.getItems().size() - o1.getItems().size();
    }
}
