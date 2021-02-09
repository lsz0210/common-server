package com.duck.common.server.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author lsz
 * @Date 2020-07-14 17:24
 */
@Getter
@Setter
public class UnifyResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public UnifyResponse(Integer code) {
        this.code = code;
    }

    public UnifyResponse(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public UnifyResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public UnifyResponse(Integer code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

}
