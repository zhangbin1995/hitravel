package com.lixiang.hitravel.redis;

/**
 * @author binzhang
 * @date 2019-12-08
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
