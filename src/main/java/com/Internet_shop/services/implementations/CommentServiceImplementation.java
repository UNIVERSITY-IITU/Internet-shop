package com.Internet_shop.services.implementations;

import com.Internet_shop.entities.Comment;
import com.Internet_shop.entities.Items;
import com.Internet_shop.repositories.CommentRepository;
import com.Internet_shop.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {
    @Autowired
    CommentRepository repository;


    @Override
    public List<Comment> getCommentsByItem(Items items) {
        return repository.findAllByItems_Id(items.getId());
    }

    @Override
    public void deleteComment(Long comment_id) {
        repository.deleteById(comment_id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public Comment getCommentById(Long comment_id) {
        return repository.findByIdEquals(comment_id);
    }
}
