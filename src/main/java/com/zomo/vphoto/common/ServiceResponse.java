package com.zomo.vphoto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ServiceResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    private ServiceResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResponse(Integer code) {
        this.code = code;
    }

    private ServiceResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServiceResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ServiceResponse<T> createSuccess(){
        return new ServiceResponse(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createSuccessMsg(String msg){
        return new ServiceResponse(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServiceResponse<T> createSuccess(T data){
        return new ServiceResponse(ResponseCode.SUCCESS.getCode(),data);
    }


    public static <T> ServiceResponse<T> createSuccess(String msg,T data){
        return new ServiceResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    public static <T> ServiceResponse<T> createError(){
        return new ServiceResponse(ResponseCode.ERROR.getCode());
    }

    public static <T> ServiceResponse<T> createErrorMsg(String msg){
        return new ServiceResponse(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServiceResponse<T> createError(Integer code,String msg){
        return new ServiceResponse(code,msg);
    }

    public static <T> ServiceResponse<T> createError(ResponseCode responseCode){
        return new ServiceResponse(responseCode.getCode(),responseCode.getMsg());
    }
    @JsonIgnore
    public boolean isSuccess(){
        return (this.code==ResponseCode.SUCCESS.getCode());
    }
}
