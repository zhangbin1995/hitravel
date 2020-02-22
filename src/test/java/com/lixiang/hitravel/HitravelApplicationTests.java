package com.lixiang.hitravel;

import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HitravelApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println("111");
    }

}
