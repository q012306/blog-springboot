package com.goodguy.blog.controller;

import com.goodguy.blog.entity.Article;
import com.goodguy.blog.entity.Comment;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.CommentService;
import com.goodguy.blog.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/api/loadcomments")
    public Result list(@RequestBody Article article) {
        return ResultFactory.buildSuccessResult(commentService.list(article.getId()));
    }

    @PostMapping("/api/pushcomments")
    public Result addOrUpdate(@Valid @RequestBody Comment comment , HttpServletRequest request) {
        comment.setCommentIp(IpUtil.getIpAddress(request));
        comment.setCommentDate(new Date());
        commentService.addOrUpdate(comment);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @PostMapping("/api/admin/commentsdelete")
    public Result delete(@RequestBody Comment comment) {
        commentService.deleteById(comment);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
