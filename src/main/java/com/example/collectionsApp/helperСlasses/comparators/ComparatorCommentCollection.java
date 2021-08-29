package com.example.collectionsApp.helperСlasses.comparators;

import com.example.collectionsApp.models.comment.CommentCollection;
import java.util.Comparator;

public class ComparatorCommentCollection implements Comparator<CommentCollection> {

    @Override
    public int compare(CommentCollection o1, CommentCollection o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
