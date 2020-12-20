package com.Internet_shop.services;

import com.Internet_shop.entities.Comment;
import com.Internet_shop.entities.Items;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByItem(Items items);
    void deleteComment(Long comment_id);
    Comment saveComment(Comment comment);
    Comment getCommentById(Long comment_id);
}
