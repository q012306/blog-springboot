package com.goodguy.blog;

import com.goodguy.blog.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(StringUtil.getRandomString(32));
    }

}
