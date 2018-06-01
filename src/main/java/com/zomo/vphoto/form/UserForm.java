package com.zomo.vphoto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;



@Data
public class UserForm {


    @NotEmpty(message = "username not null")
    private String username;
    
    private String password;

    private String resetPassword;

    private String repeatPassword;

    @NotEmpty(message = "name not null")
    private String name;
}
