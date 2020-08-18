package com.goodguy.blog.controller;

import com.goodguy.blog.entity.Article;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("/api/findarticle")
    public Result findArticle(@RequestBody Map map) {
        return ResultFactory.buildSuccessResult(articleService.find((String)map.get("keyword")));
    }

    @PostMapping("/api/admin/content/article")
    public Result saveArticle(@RequestBody @Valid Article article) {
        articleService.addOrUpdate(article);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @GetMapping("/api/article/{size}/{page}")
    public Result listArticles(@PathVariable("size") int size, @PathVariable("page") int page) {
        return ResultFactory.buildSuccessResult(articleService.list(page - 1, size));
    }

    @GetMapping("/api/article/{id}")
    public Result getOneArticle(@PathVariable("id") int id) {
        return ResultFactory.buildSuccessResult(articleService.findById(id));
    }

    @DeleteMapping("/api/admin/content/article/{id}")
    public Result deleteArticle(@PathVariable("id") int id) {
        articleService.delete(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }

}
