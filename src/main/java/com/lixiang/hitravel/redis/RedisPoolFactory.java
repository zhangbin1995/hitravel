package com.lixiang.hitravel.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

/**
 * @author binzhang
 * @date 2019-12-08
 */
@Service
public class RedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        HashMap<String, HashMap<String, String>> jedisInfo = redisConfig.getJedis();
        HashMap<String, String> map = jedisInfo.get("pool");

        poolConfig.setMaxTotal(Integer.valueOf(map.get("max-active")));
        poolConfig.setMaxIdle(Integer.valueOf(map.get("max-idle")));
        poolConfig.setMaxWaitMillis(Integer.valueOf(map.get("max-wait")) * 1000);  //注意单位是ms，要转换单位
        poolConfig.setMinIdle(Integer.valueOf(map.get("min-idle")));
        return new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
    }
}
