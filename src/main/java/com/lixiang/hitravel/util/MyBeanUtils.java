package com.lixiang.hitravel.util;

import org.springframework.beans.BeanUtils;

/**
 * @author zhang
 * @date 2020-01-19
 */
public class MyBeanUtils<Dto, Vo> {

    /**
     * dot 转换为Vo 工具类
     *
     * @param dtoEntity
     * @param doClass
     * @return
     */
    public static <Vo> Vo dtoToVo(Object dtoEntity, Class<Vo> doClass) {
        // 判断dto是否为空!
        if (dtoEntity == null) {
            return null;
        }
        // 判断VoClass 是否为空
        if (doClass == null) {
            return null;
        }
        try {
            Vo newInstance = doClass.newInstance();
            BeanUtils.copyProperties(dtoEntity, newInstance);
            // Dto转换Vo
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * do 转换为Dto 工具类
     *
     * @param voEntity
     * @param dtoClass
     * @return
     */
    public static <Dto> Dto doToDto(Object voEntity, Class<Dto> dtoClass) {
        // 判断dto是否为空!
        if (voEntity == null) {
            return null;
        }
        // 判断VoClass 是否为空
        if (dtoClass == null) {
            return null;
        }
        try {
            Dto newInstance = dtoClass.newInstance();
            BeanUtils.copyProperties(voEntity, newInstance);
            // Dto转换Vo
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }
}


