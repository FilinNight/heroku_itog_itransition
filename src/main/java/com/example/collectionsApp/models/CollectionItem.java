package com.example.collectionsApp.models;


import com.example.collectionsApp.helperСlasses.comparators.ComparatorCommentCollection;
import com.example.collectionsApp.models.comment.CommentCollection;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "collections")
public class CollectionItem {

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
    private User user;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.REMOVE)
    private List<CommentCollection> comments;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.REMOVE)
    private List<Item> items;

    public List<CommentCollection> getComments() {
        if(comments.size()!=0)
            Collections.sort(comments, new ComparatorCommentCollection());
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

    public int sizeItems (){
        return getItems().size();
    }

    @Override
    public String toString() {
        return "CollectionItem:" + '\n' +
                "id = " + id + '\n' +
                "name = " + name + '\n' +
                "description = " + description + '\n' +
                "user = " + user.getUsername() + '\n' +
                "items size = " + items.size() + '\n' +
                "date = " + getFormatDate() + '\n' +
                "time = " + getFormatTime();
    }
}


