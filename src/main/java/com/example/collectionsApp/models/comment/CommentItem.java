package com.example.collectionsApp.models.comment;

import com.example.collectionsApp.models.Item;
import lombok.Data;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "comments_item")
public class CommentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;
    private String user_name;
    private Date date;

    @ManyToOne
    @JoinColumn
    private Item item;


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
        return "CommentItem:" + '\n' +
                "id = " + id + '\n' +
                "comment = " + comment + '\n' +
                "item = " + item.getName() + '\n' +
                "user = " + user_name + '\n' +
                "date = " + getFormatDate() + '\n' +
                "time = " + getFormatTime();
    }
}
