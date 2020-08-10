package com.goodguy.blog.service;

import com.goodguy.blog.dao.CommentDAO;
import com.goodguy.blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    RedisService redisService;

    public List<Comment> list(int id) {
        List<Comment> comment;
        String key = "articlecomment:" + id;
        Object articlecommentCache = redisService.get(key);
        if(articlecommentCache == null){
            comment = commentDAO.findByCommentArticleId(id);
            redisService.set(key, comment);
        }else{
            comment = (List<Comment>) articlecommentCache;
        }
        return comment;
    }

    public void addOrUpdate(Comment comment) {
        redisService.delete("articlecomment:" + comment.getCommentArticleId());
        commentDAO.save(comment);
    }

    public void deleteById(Comment comment) {
        redisService.delete("articlecomment:" + comment.getCommentArticleId());
        commentDAO.deleteById(comment.getId());
    }
}
