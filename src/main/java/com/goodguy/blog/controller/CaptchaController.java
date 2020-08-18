package com.goodguy.blog.controller;

import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import com.goodguy.blog.util.JWTUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CaptchaController {
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @GetMapping("/api/getcaptcha")
    public Result GetCaptcha(){
        String createText = defaultKaptcha.createText();
        // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage bufferedImage = defaultKaptcha.createImage(createText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
            // 将图片转换成base64格式进行存储
            BASE64Encoder encoder = new BASE64Encoder();
            String imageString = encoder.encode(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
            // 签名,密码为验证码的值
            String captchaToken = JWTUtil.CreateCaptchaToken(createText);
            Map map = new HashMap();
            map.put("captchaToken",captchaToken);
            map.put("imageString",imageString);
            return ResultFactory.buildSuccessResult(map);
        } catch (IOException e) {
            return ResultFactory.buildFailResult("验证码创建失败");
        }
    }
}
