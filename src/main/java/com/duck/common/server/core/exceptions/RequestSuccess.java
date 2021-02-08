package com.duck.common.server.core.exceptions;


import com.duck.common.server.core.exceptions.http.HttpException;

/**
 * @Author lsz
 * @Date 2020-07-25 14:45
 */
public class RequestSuccess extends HttpException {
    public RequestSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
}
