package com.lixiang.hitravel.enums;

/**
 * @author binzhang
 * @date 2020-01-16
 */
public enum ScenicEnum {

    // 审核状态 0-未激活 1-已激活 2-已删除
    STATUS_INVALID(0, "未激活"),
    STATUS_VALID(1, "已激活"),
    STATUS_DELETE(2, "已删除");

    private int code;

    private String msg;

    private ScenicEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ScenicEnum stateOf(int index) {
        for (ScenicEnum state : values()) {
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
