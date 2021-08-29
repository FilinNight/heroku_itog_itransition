package com.example.collectionsApp.models;

import com.example.collectionsApp.helperСlasses.comparators.ComparatorCommentItem;
import com.example.collectionsApp.models.comment.CommentItem;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Column(length = 2000)
    private String description;
    private Date date;
    private String url;

    @ManyToOne
    @JoinColumn
    private CollectionItem collection;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<CommentItem> comments;

    public List<CommentItem> getComments() {
        if(comments.size()!=0)
            Collections.sort(comments, new ComparatorCommentItem());
        return comments;
    }

    public String getFormatDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(getDate());
    }

    public String getFormatTime(){
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        return formater.format(getDate());
    }

    @Override
    public String toString() {
        return "Item:" + '\n' +
                "id = " + id + '\n' +
                "name = " + name + '\n' +
                "description = " + description + '\n' +
                "collection = " + collection.getName() + '\n' +
                "user = " + collection.getUser().getUsername() + '\n' +
                "date = " + getFormatDate() + '\n' +
                "time = " + getFormatTime() + '\n' +
                "url = " + url;
    }
}
