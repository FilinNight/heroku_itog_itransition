package com.example.collectionsApp.helperСlasses.comparators;

import com.example.collectionsApp.models.Item;
import java.util.Comparator;

public class ComparatorSortByDataItems implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
