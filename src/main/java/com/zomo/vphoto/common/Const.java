package com.zomo.vphoto.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


public class Const {
    public static final String CURRENT_USER="user";

    public static final String QINIU_CDN_PREFIX="http://image.compress.zomo-studio.com/";

    public static final String FORM_IMAGE_TYPE_BANNER="banner";

    public static final String FORM_IMAGE_TYPE_KEYIAMGE="keyImage";

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
    @Getter
    public enum DetailStatus{
        ONLINE(0,"在线"),
        OFFLINE(1,"下线");
        private Integer code;
        private String msg;

        DetailStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
    @Getter
    public enum ProjectStatus{
        ONLINE(0,"在线"),
        OFFLINE(1,"下线");
        private Integer code;
        private String msg;

        ProjectStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
        public static String getName(Integer code){
            for (ProjectStatus status:ProjectStatus.values()){
                if (status.getCode()==code){
                    return status.getMsg();
                }
            }
                return null;
        }
    }
}
