package com.zomo.vphoto.controller;

import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.service.IProjectService;
import com.zomo.vphoto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/project/")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "findAll.do",method = RequestMethod.GET)
    public String findAll(HttpSession session, Model model){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO)||userService.isManager(userVO)){

        }
        return null;

    }

}
