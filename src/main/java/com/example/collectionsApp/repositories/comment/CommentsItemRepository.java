package com.example.collectionsApp.repositories.comment;


import com.example.collectionsApp.models.comment.CommentItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsItemRepository  extends JpaRepository<CommentItem, Long> {

    List<CommentItem> findAllByItem_Id(Long id);
}
