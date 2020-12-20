package com.Internet_shop.repositories;

import com.Internet_shop.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByItems_Id(Long item_id);
    Comment findByIdEquals(Long id);
}
