/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-01-10 16:14
 */
package com.duck.common.server.core.exceptions.http;

import com.duck.common.server.core.enumerations.Code;
import org.springframework.http.HttpStatus;

/**
 * @author 5duck
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = HttpStatus.FORBIDDEN.value();
    }

    public ForbiddenException(String message) {
        super(message);
        this.code = Code.FORBIDDEN.getCode();
        this.httpStatusCode = HttpStatus.FORBIDDEN.value();
    }

    public ForbiddenException(int code, String message) {
        super(code, message);
        this.code = code;
        this.httpStatusCode = HttpStatus.FORBIDDEN.value();
    }
}
