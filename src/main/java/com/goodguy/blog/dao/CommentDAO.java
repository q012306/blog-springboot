package com.goodguy.blog.dao;

import com.goodguy.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<Comment,Integer> {
    List<Comment> findByCommentArticleId(int commentArticleId);
}
