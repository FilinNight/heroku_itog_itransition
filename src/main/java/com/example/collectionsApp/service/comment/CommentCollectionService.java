package com.example.collectionsApp.service.comment;

import com.example.collectionsApp.models.CollectionItem;
import com.example.collectionsApp.models.comment.CommentCollection;
import com.example.collectionsApp.repositories.comment.CommentsCollectionRepository;
import com.example.collectionsApp.service.CollectionService;
import com.example.collectionsApp.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentCollectionService {

    @Autowired
    private CommentsCollectionRepository commentsRepository;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private UserService userService;

    private static final Logger log = Logger.getLogger(CommentCollectionService.class);


    public boolean add(String message, Long collection_id){
        CollectionItem collection = collectionService.findById(collection_id);

        try {
            if(collection != null && userService.isAuthentication() && message.replaceAll("\\s+","").length()!=0){
                CommentCollection comment = new CommentCollection();
                comment.setCollection(collection);
                comment.setComment(message);
                comment.setDate(new Date());
                comment.setUser_name(userService.getAuthenticationName());
                commentsRepository.save(comment);
                log.info("add comment collection. User: " + comment.getUser_name() + ", collection: " + comment.getCollection().getName());
                return true;
            }
            else {
                log.debug("add comment collection. (collection does not exist or user is not authentication)");
                return false;
            }
        }
        catch (Exception e){
            log.error("add comment collection.");
            log.error(e.getMessage());
            return false;
        }

    }

    public boolean delete(Long id){
        CommentCollection comment = commentsRepository.getById(id);
        try {
            if(comment != null){
                commentsRepository.deleteById(id);
                log.info("delete comment collection. User: " + comment.getUser_name() + ", collection: " + comment.getCollection().getName());
                return true;
            }
            else {
                log.debug("delete comment collection. (collection does not exist or user is not authentication)  User: " + comment.getUser_name() + ", collection: " + comment.getCollection().getName());
                return false;
            }
        }catch (Exception e){
            log.error("delete comment collection. User: " + comment.getUser_name() + ", collection: " + comment.getCollection().getName());
            log.error(e.getMessage());
            return false;
        }
    }

    public List<CommentCollection> findAllByCollectionId(Long collection_id){
        return commentsRepository.findAllByCollection_Id(collection_id);
    }

}
