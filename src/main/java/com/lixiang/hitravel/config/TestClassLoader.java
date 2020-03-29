package com.lixiang.hitravel.config;

import java.lang.reflect.Method;

/**
 * @author binzhang
 * @date 2020-03-12
 */
public class TestClassLoader {

    public static void main(String[] args) throws Exception{
        HClassLoader myClassLoader = new HClassLoader("e:/temp/a");
        Class clazz = myClassLoader.loadClass("com.lixiang.hitravel.config.Car");
        Object o = clazz.newInstance();
        Method print = clazz.getDeclaredMethod("print", null);
        print.invoke(o, null);
    }
}
