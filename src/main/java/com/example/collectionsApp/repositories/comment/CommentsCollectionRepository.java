package com.example.collectionsApp.repositories.comment;


import com.example.collectionsApp.models.comment.CommentCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsCollectionRepository extends JpaRepository<CommentCollection, Long> {

    List<CommentCollection> findAllByCollection_Id(Long id);
}
