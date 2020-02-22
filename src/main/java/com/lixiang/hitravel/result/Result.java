package com.lixiang.hitravel.result;

import lombok.Data;

/**
 * @author binzhang
 * @date 2019-12-07
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm, T data) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
        this.data = data;
    }

    /**
     * 成功时候的调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T>Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     * @param cm
     * @param <T>
     * @return
     */
    public static <T>Result<T> error(CodeMsg cm, T data) {
        return new Result<T>(cm, data);
    }
}
