package com.zomo.vphoto.form;

import lombok.Data;

@Data
public class UserForm {
    
    private String username;
    
    private String password;

    private String resetPassword;

    private String repeatPassword;

    private String name;
}
