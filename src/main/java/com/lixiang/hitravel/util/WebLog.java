package com.lixiang.hitravel.util;

import java.lang.annotation.*;

// 什么时候使用该注解，我们定义为运行时
@Retention(RetentionPolicy.RUNTIME)
// 注解用于什么地方，我们定义为作用与方法上
@Target({ElementType.METHOD})
// 注解是否将包含在JavaDoc中
@Documented
// 注解名为WebLog
public @interface WebLog {
    /**
     * 日志描述信息
     *
     * @return
     */
    String description() default "";

}