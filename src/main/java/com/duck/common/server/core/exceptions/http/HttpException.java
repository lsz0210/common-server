package com.duck.common.server.core.exceptions.http;

import com.duck.common.server.core.enumerations.Code;
import org.springframework.http.HttpStatus;

/**
 * @Author lsz
 * @Date 2020-07-15 16:08
 */
public class HttpException extends RuntimeException {
    protected String message;
    protected Integer code;
    protected Integer httpStatusCode;

    public HttpException() {
        super(Code.INTERNAL_SERVER_ERROR.getZhDescription());
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.code = Code.INTERNAL_SERVER_ERROR.getCode();
    }

    public HttpException(String message){
        super(message);
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.code = Code.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
    }

    public HttpException(int code, String message) {
        super(message);
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.code = code;
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
