package com.zomo.vphoto.controller;

import com.zomo.vphoto.form.UserForm;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 登录功能c
     * @param username
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value ="/login.do",method = RequestMethod.POST)
    public String login(@RequestParam(value = "username",required = true) String username,
                        @RequestParam(value = "password",required = true) String password,
                        Model model,
                        HttpSession session){

        if (StringUtils.isEmpty(username)){
            model.addAttribute("msg","用户名为空请重新输入");
            return "login";
        }

        if (StringUtils.isEmpty(password)){
            model.addAttribute("msg","密码为空请重新输入");
            return "login";
        }

        ServiceResponse response=userService.login(username,password);
        if (!response.isSuccess()){
            model.addAttribute("msg",response.getMsg());
            return "login";
        }

        session.setAttribute(Const.CURRENT_USER,response.getData());

        return "index";
    }

    /**
     * 用户登出
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    public String logout(HttpSession session,Model model){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","登出错误");
                return "error";
        }
        session.removeAttribute(Const.CURRENT_USER);
        model.addAttribute("msg","登出成功");
        return "success";
    }

    /**
     * 用户注册
     * @param userForm
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public String userRegister(UserForm userForm, Integer roleId,Model model){
        if (userForm==null){
            model.addAttribute("msg","用户信息为空请重新提交");
            return "register";
        }
        if (StringUtils.isEmpty(userForm.getName())){
            model.addAttribute("msg","名字不能为空");
            return "register";
        }
        if (roleId==1||roleId==2){
            if (roleId==1){
                ServiceResponse response=userService.registerManager(userForm);
                if (response.isSuccess()){
                    return "success";
                }
                model.addAttribute("msg",response.getMsg());
                return "register";
            }else {
                ServiceResponse response=userService.registerUser(userForm);
                if (response.isSuccess()){
                    return "success";
                }
                model.addAttribute("msg",response.getMsg());
                return "register";
            }
        }
        model.addAttribute("msg","请选择新用户职位");
        return "register";
    }

    @RequestMapping(value="resetPassword.do",method = RequestMethod.POST)
    public String resetPassword(UserForm userForm,HttpSession session,Model model){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","用户登录信息失效请重新登录");
            return "error";
        }
        if (StringUtils.isEmpty(userForm.getPassword())){
            model.addAttribute("msg","密码不能为空，请重新输入");
            return "resetPassword";
        }
        if (StringUtils.isEmpty(userForm.getResetPassword())){
            model.addAttribute("msg","新密码不能为空，请重新输入");
            return "resetPassword";
        }
        if (StringUtils.isEmpty(userForm.getRepeatPassword())){
            model.addAttribute("msg","确认密码不能为空，请重新输入");
            return "resetPassword";
        }
        if(!userForm.getRepeatPassword().equals(userForm.getResetPassword())){
            model.addAttribute("msg","密码不一致请重新输入，请重新输入");
            return "resetPassword";
        }
        ServiceResponse response=userService.resetPassword(userForm,userVO);
        if (!response.isSuccess()){
            model.addAttribute("msg",response.getMsg());
            return "resetPassword";
        }
        return "success";
    }
}
