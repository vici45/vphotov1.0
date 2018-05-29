package com.zomo.vphoto.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200,"success"),
    ERROR(404,"error");
    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
