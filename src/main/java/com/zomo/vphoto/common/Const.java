package com.zomo.vphoto.common;

import lombok.Getter;

public class Const {
    public static final String CURRENT_USER="user";
    @Getter
    public enum Role{
        ADMIN(0,"admin"),
        MANAGER(1,"manager"),
        user(2,"user");
        private Integer code;
        private String msg;

        Role(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
