package com.goodguy.blog.controller;

import com.goodguy.blog.entity.Article;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.ArticleService;
import com.goodguy.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.DigestUtils;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    @PostMapping("/api/admin/content/upload")
    public Result coversUpload(MultipartFile file) {
        try {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            File imageFolder = new File(path,"static/images/upload/");
            File f = new File(imageFolder, DigestUtils.md5DigestAsHex(file.getBytes()) + file.getOriginalFilename()
                    .substring(file.getOriginalFilename().length() - 4));
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            file.transferTo(f);
            String imgUrl = "http://localhost:8443/api/file/" + f.getName();
            return ResultFactory.buildSuccessResult(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("上传失败");
        }
    }
}
