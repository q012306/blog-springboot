package com.goodguy.blog.controller;

import com.goodguy.blog.entity.Friend;
import com.goodguy.blog.entity.PageLink;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.FriendService;
import com.goodguy.blog.service.PageLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PageLinkController {
    @Autowired
    PageLinkService pageLinkService;

    @GetMapping("/api/loadpagelinks")
    public Result list() {
        return ResultFactory.buildSuccessResult(pageLinkService.list());
    }

    @PostMapping("/api/admin/addpagelinks")
    public Result addOrUpdate(@Valid @RequestBody PageLink pageLink) {
        pageLinkService.addOrUpdate(pageLink);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @PostMapping("/api/admin/deletepagelinks")
    public Result delete(@RequestBody PageLink pageLink) {
        pageLinkService.deleteById(pageLink);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
