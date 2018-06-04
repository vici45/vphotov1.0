package com.zomo.vphoto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;


@Data
public class UserForm {

    @Length(min =6,max = 16,message = "用户名必须在6在16位之间")
    private String username;

    @Length(min = 8,max = 16 ,message = "密码必须在8到20位之间")
    private String password;

    @Length(min = 8,max = 16 ,message = "密码必须在8到20位之间")
    private String resetPassword;

    @Length(min = 8,max = 16 ,message = "密码必须在8到20位之间")
    private String repeatPassword;

    @Pattern(regexp = "^([\\u2E80-\\u9FFF]){2,6}$",message = "用户姓名必须为2到6位中文")
    private String name;
}
