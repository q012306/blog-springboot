package com.goodguy.blog.controller;

import com.goodguy.blog.entity.Friend;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class FriendController {
    @Autowired
    FriendService friendService;

    @GetMapping("/api/loadfriends")
    public Result list() {
        return ResultFactory.buildSuccessResult(friendService.list());
    }

    @PostMapping("/api/admin/savefriends")
    public Result addOrUpdate(@Valid @RequestBody Friend friend) {
        friendService.addOrUpdate(friend);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @PostMapping("/api/admin/deletefriends")
    public Result delete(@RequestBody Friend friend) {
        friendService.deleteById(friend);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
