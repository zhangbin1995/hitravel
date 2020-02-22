package com.lixiang.hitravel.redis;

/**
 * @author binzhang
 * @date 2019-12-08
 */
public class UserKey extends BasePrefix {

    private UserKey(int expireTime, String prefix) {
        super(expireTime,prefix);
    }

    public static UserKey token = new UserKey(3600,"token");
}
