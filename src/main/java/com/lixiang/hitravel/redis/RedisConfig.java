package com.lixiang.hitravel.redis;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author binzhang
 * @date 2019-12-08
 */

@Component
@ConfigurationProperties("spring.redis")
@Data
@NoArgsConstructor
public class RedisConfig {

    private String host;

    private int port;

    private int timeout;//秒

    private String password;

    private HashMap<String, HashMap<String, String>> jedis = new HashMap<>();

}


