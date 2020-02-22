package com.lixiang.hitravel.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author binzhang
 * @date 2019-12-08
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "daxiang666";

    public static String inputPassToDBPass(String inputPass) {
        String str = inputPass + salt;
        return md5(str);
    }

    public static String formPassToDBPass(String inputPass, String salt) {
        String str = inputPass + salt;
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB) {
//        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(input, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456", "daxiang666"));
    }

}
