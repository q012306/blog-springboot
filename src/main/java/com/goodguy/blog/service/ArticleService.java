package com.goodguy.blog.service;

import com.goodguy.blog.dao.ArticleDAO;
import com.goodguy.blog.entity.Article;
import com.goodguy.blog.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleService {
    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    RedisService redisService;

    public MyPage list(int page, int size) {
        MyPage<Article> articles;
        String key = "articlepage:" + size + "/" + page;
        Object articlePageCache = redisService.get(key);
        if (articlePageCache == null) {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Page<Article> articlesInDb = articleDAO.findAll(PageRequest.of(page, size, sort));
            articles = new MyPage<>(articlesInDb);
            redisService.set(key, articles);
        }else{
            articles = (MyPage<Article>) articlePageCache;
        }
        return articles;
    }

    public Article findById(int id) {
        Article article;
        String key = "article:" + id;
        Object articleCache = redisService.get(key);
        if (articleCache == null) {
            article = articleDAO.findById(id);
            redisService.set(key, article);
        }else{
            article = (Article) articleCache;
        }
        return article;
    }

    public void addOrUpdate(Article article) {
        redisService.delete("article:" + article.getId());
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
        articleDAO.save(article);
    }

    public void delete(int id) {
        redisService.delete("article:" + id);
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
        articleDAO.deleteById(id);
    }

    public List<Article> find(String keyword) {
        return articleDAO.findByArticleContentMdContainingOrArticleTitleContaining(keyword,keyword);
    }
}
