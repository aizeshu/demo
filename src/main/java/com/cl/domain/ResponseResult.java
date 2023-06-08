package com.cl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseResult {
    private static final int SUCCESS = 200;
    private static final String SUCCESS_MSG = "success";
    private static final int FAIL = 500;

    private int code;
    private String message;
    private Object data;

    private ResponseResult() {
    }

    public static ResponseResult success(Object data) {
        ResponseResult success = new ResponseResult(SUCCESS, SUCCESS_MSG, data);
        return success;
    }

    public static ResponseResult fail(String msg) {
        ResponseResult fail = new ResponseResult(FAIL, msg, null);
        return fail;
    }


}
