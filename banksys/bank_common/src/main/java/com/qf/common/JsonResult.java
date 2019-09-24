package com.qf.common;

import lombok.Data;

@Data
public class JsonResult {

    public JsonResult() {
    }

    public JsonResult(Integer code, Object info) {
        this.code = code;
        this.info = info;
    }

    private Integer code;//0表示正确，1表示错误
    private Object info;//具体信息
}
