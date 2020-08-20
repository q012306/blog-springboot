package com.goodguy.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodguy.blog.entity.User;
import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.service.UserService;
import com.goodguy.blog.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/api/login")
    public Result login(@RequestBody Map loginMap) {
        if(!JWTUtil.VerifyCaptchaToken((String)loginMap.get("captchaToken"),(String)loginMap.get("captchaText")))
            return ResultFactory.buildFailResult("验证码错误");
        String username = (String) loginMap.get("username");
        // 对 html 标签进行转义，防止 XSS 攻击
        username = HtmlUtils.htmlEscape(username);
        User user = userService.get(username,(String)loginMap.get("password"));
        if (null == user) {
            return ResultFactory.buildFailResult("账号不存在");
        } else {
            Map login_user = new HashMap();
            login_user.put("token",JWTUtil.CreateToken(user));
            login_user.put("status",user.getStatus());
            login_user.put("username",user.getUsername());
            return ResultFactory.buildSuccessResult(login_user);
        }
    }

    @GetMapping(value = "/api/info")
    public Result getInfo(HttpServletRequest httpServletRequest){
        Map token = JWTUtil.VerifyToken(httpServletRequest.getHeader("Authorization").replace("Bearer ", ""));
        User user = userService.getById((int)token.get("userId"));
        return ResultFactory.buildSuccessResult(user);
    }

    @PostMapping(value = "/api/register")
    public Result register(@RequestBody Map registerMap){
        if(!JWTUtil.VerifyCaptchaToken((String)registerMap.get("captchaToken"),(String)registerMap.get("captchaText")))
            return ResultFactory.buildFailResult("验证码错误");
        else if( userService.isExist((String)registerMap.get("username")) )
            return ResultFactory.buildFailResult("用户名已存在");
        else{
            String userJson = JSONObject.toJSONString(registerMap.get("user"));
            User user = JSONObject.parseObject(userJson,User.class);
            user.setStatus(0);
            userService.add(user);
            return ResultFactory.buildSuccessResult("注册成功");
        }
    }

}
