package com.example.collectionsApp.helperСlasses.comparators;


import com.example.collectionsApp.models.comment.CommentItem;
import java.util.Comparator;

public class ComparatorCommentItem implements Comparator<CommentItem> {

    @Override
    public int compare(CommentItem o1, CommentItem o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
