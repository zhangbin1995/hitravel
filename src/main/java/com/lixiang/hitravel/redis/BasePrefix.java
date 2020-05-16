package com.lixiang.hitravel.redis;

/**
 * @author zhang
 * @date 2019-12-08
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        // 0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 过期时间
     * @return
     */
    public int expireSeconds() {
        // 默认0 代表永不过期
        return expireSeconds;
    }

    /**
     * key前缀
     * @return
     */
    public String getPrefix() {
        return prefix;
    }
}
