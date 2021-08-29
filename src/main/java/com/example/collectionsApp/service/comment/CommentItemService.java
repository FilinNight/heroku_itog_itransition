package com.example.collectionsApp.service.comment;

import com.example.collectionsApp.models.Item;
import com.example.collectionsApp.models.comment.CommentItem;
import com.example.collectionsApp.repositories.comment.CommentsItemRepository;
import com.example.collectionsApp.service.ItemService;
import com.example.collectionsApp.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentItemService {

    @Autowired
    private CommentsItemRepository commentsRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    private static final Logger log = Logger.getLogger(CommentItemService.class);

    public boolean add(String message, Long item_id){

        Item itemDB = itemService.findById(item_id);
        try {
            if(itemDB != null && userService.isAuthentication() && message.replaceAll("\\s+","").length()!=0){
                CommentItem comment = new CommentItem();
                comment.setComment(message);
                comment.setItem(itemDB);
                comment.setDate(new Date());
                comment.setUser_name(userService.getAuthenticationName());
                commentsRepository.save(comment);
                log.info("add comment item. User: " + comment.getUser_name() + ", item: " + comment.getItem().getName());
                return true;
            }
            else {
                log.debug("add comment item. (collection does not exist or user is not authentication");
                return false;
            }
        }
        catch (Exception e){
            log.error("add comment item.");
            log.error(e.getMessage());
            return false;
        }

    }

    public boolean delete(Long id){
        CommentItem comment = commentsRepository.getById(id);
        try {
            if(comment != null){
                commentsRepository.deleteById(id);
                log.info("delete comment collection. User: " + comment.getUser_name() + ", item: " + comment.getItem().getName());
                return true;
            }
            else {
                log.debug("delete comment item. (collection does not exist or user is not authentication)  User: " + comment.getUser_name() + ", collection: " + comment.getItem().getName());
                return false;
            }
        }catch (Exception e){
            log.error("delete comment collection. User: " + comment.getUser_name() + ", item: " + comment.getItem().getName());
            log.error(e.getMessage());
            return false;
        }
    }

    public List<CommentItem> findAllByItemId(Long item_id){
        return commentsRepository.findAllByItem_Id(item_id);
    }

    public List<CommentItem> findAll(){
        return commentsRepository.findAll();
    }
}
