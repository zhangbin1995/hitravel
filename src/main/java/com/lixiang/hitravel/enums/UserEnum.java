package com.lixiang.hitravel.enums;

/**
 * @author zhang
 * @date 2020-01-16
 */
public enum UserEnum {

    STATUS_INVALID(0, "账号不可用"),
    STATUS_VALID(1, "账号可用"),
    STATUS_DELETE(2, "账号已删除"),
    TYPE_ROOT(0, "管理员"),
    TYPE_USER(1, "普通用户"),
    TYPE_BUSINESS(2, "商家");

    private int code;

    private String msg;

    private UserEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static UserEnum stateOf(int index) {
        for (UserEnum state : values()) {
            if (state.getCode() == index) {
                return state;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
