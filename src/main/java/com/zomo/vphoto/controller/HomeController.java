package com.zomo.vphoto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/project/addProject")
    public String addProject(){
        return "addProject";
    }


}
