package com.goodguy.blog.controller;

import com.goodguy.blog.entity.User;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.UserService;
import com.goodguy.blog.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/api/login")
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            return ResultFactory.buildFailResult("账号不存在");
        } else {
            Map login_user = new HashMap();
            login_user.put("token",JWTUtil.CreateToken(user));
            login_user.put("status",user.getStatus());
            return ResultFactory.buildSuccessResult(login_user);
        }
    }
}
