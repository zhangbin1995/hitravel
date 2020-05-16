package com.lixiang.hitravel.result;

import lombok.Data;

/**
 * @author zhang
 * @date 2019-12-07
 */
@Data
public class CodeMsg {
    private int code;
    private String msg;

    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg ERROR = new CodeMsg(201, "操作失败");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500100, "参数校验异常");
    public static CodeMsg TOEKN_IS_REQUIRED = new CodeMsg(500101, "无token，请重新登录");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500102, "用户不存在，请重新登录");



    // 登录模块 5001xx
    public static CodeMsg SESSION_ERROR = new CodeMsg(500201, "session不存在或者已经失效");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500202, "手机号不能为空");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500203, "登录密码不能为空");
    public static CodeMsg MOBILE_PATTERN_ERROR = new CodeMsg(500204, "手机号码格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500205, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500206, "密码错误");

    // 商品模块 5002xx

    // 订单模块 5003xx
    // ...


    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
