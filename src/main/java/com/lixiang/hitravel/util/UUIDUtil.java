package com.lixiang.hitravel.util;

import java.util.UUID;

/**
 * @author zhang
 * @date 2019-12-09
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
