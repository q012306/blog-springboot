package com.goodguy.blog.dao;

import com.goodguy.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDAO extends JpaRepository<Article,Integer> {
    Article findById(int id);
    List<Article> findByArticleContentMdContainingOrArticleTitleContaining(String keyword1,String keyword2);

}
