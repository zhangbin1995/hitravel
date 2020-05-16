package com.lixiang.hitravel.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhang
 * @date 2019-12-09
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile{
    /*默认必须 有值*/
    boolean required() default true;
    /*如果校验不通过 输出 信息*/
    String message() default "手机号码格式不对啊！";

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};
}