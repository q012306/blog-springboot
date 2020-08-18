package com.goodguy.blog.util;


import com.goodguy.blog.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String SECRET = "@*#&$^%GoodGuy1145141919";

    /**
     * 用户鉴权
     * @param user
     * @return
     */
    public static String CreateToken(User user){
        // expire time
        Calendar nowTime = Calendar.getInstance();
        // 有效期
        nowTime.add(Calendar.HOUR, 12);
        Date expiresDate = nowTime.getTime();
        Claims claims = Jwts.claims();
        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
        claims.put("status", user.getStatus());
        claims.setIssuer("GoodGuy");
        String token = Jwts.builder().setClaims(claims).setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return token;
    }

    public static Map VerifyToken(String token) {
        Map user = new HashMap();
        try{
            Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            //String signature = jws.getSignature();
            //Map<String, String> header = jws.getHeader();
            Claims Claims = jws.getBody();
            user.put("username", Claims.get("username"));
            user.put("userId", Claims.get("userId"));
            user.put("status", Claims.get("status"));
            return user;
        }catch (Exception e){
            System.out.println("解析token错误！");
            return null;
        }
    }

    /**
     * 验证码
     * @param CaptchaText
     * @return
     */
    public static String CreateCaptchaToken(String CaptchaText){
        // expire time
        Calendar nowTime = Calendar.getInstance();
        // 有效期
        nowTime.add(Calendar.SECOND, 120);
        Date expiresDate = nowTime.getTime();
        String token = Jwts.builder().setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS256, CaptchaText+"GoodGuyPutSomeSaltInHere")
                .compact();
        return token;
    }

    public static Boolean VerifyCaptchaToken(String token,String CaptchaText){
        try{
            Jwts.parser().setSigningKey(CaptchaText+"GoodGuyPutSomeSaltInHere").parseClaimsJws(token);
            return true;
        }catch (Exception e){
            System.out.println("解析验证码token错误！");
            return false;
        }
    }

}

