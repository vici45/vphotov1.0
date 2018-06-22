package com.zomo.vphoto.controller;

import com.zomo.vphoto.DTO.QiNiuPutRet;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.form.ProjectForm;
import com.zomo.vphoto.service.IProjectService;
import com.zomo.vphoto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/project/")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IUserService userService;
    @Autowired
    private FileUploadController fileUploadController;

    @RequestMapping(value = "findAll.do",method = RequestMethod.GET)
    public String findAll(HttpSession session, Model model){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO)||userService.isManager(userVO)){
            ServiceResponse response=projectService.findAllProject();
            if (response.isSuccess()){
                model.addAttribute("list",response.getData());
                return "list";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }
        }
        ServiceResponse response=projectService.findProjectByRetoucherId(userVO);
        if (response.isSuccess()){
            model.addAttribute("list",response.getData());
            return "list";
        }
        model.addAttribute("msg",response.getMsg());
        return "error";

    }

    @RequestMapping(value = "addProject.do",method = RequestMethod.POST)
    public String addProject(@Valid ProjectForm projectForm,
                             BindingResult result,
                             Model model,
                             HttpSession session,
                             @RequestParam(value = "banner", required = false) MultipartFile banner,
                             @RequestParam(value = "keyImage", required = false) MultipartFile keyImage) throws Exception {
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","用户登录超时，请重新登录");
            return "error";
        }
            if (userService.isAdmin(userVO)||userService.isManager(userVO)){
            if (result.hasErrors()){
                model.addAttribute("msg",result.getAllErrors().get(0).getDefaultMessage());
                return "forward:/project/addProjectPage.do";
            }
           /* if (!banner.isEmpty()) {
                ServiceResponse bannerResponse = fileUploadController.uploadFile(banner);
                if (!bannerResponse.isSuccess()) {
                    model.addAttribute("msg", bannerResponse.getMsg());
                    return "error";
                }
                QiNiuPutRet bannerRet = (QiNiuPutRet) bannerResponse.getData();
                String bannerHost = Const.QINIU_CDN_PREFIX + bannerRet.getKey();
                projectForm.setProjectBannerHost(bannerHost);
            }*/

      /*      ServiceResponse keyImageResponse=fileUploadController.uploadFile(keyImage);
            if (!keyImageResponse.isSuccess()){
                model.addAttribute("msg",keyImageResponse.getMsg());
            }
            QiNiuPutRet keyImageRet= (QiNiuPutRet) keyImageResponse.getData();
            String keyImageHost=Const.QINIU_CDN_PREFIX+keyImageRet.getKey();
            projectForm.setProjectKeyImageHost(keyImageHost);*/
             ServiceResponse bannerResponse=formFileUpload(projectForm,banner,Const.FORM_IMAGE_TYPE_BANNER);
                if (!bannerResponse.isSuccess()){
                    model.addAttribute("msg",bannerResponse.getMsg());
                    return "error";
                }
             ServiceResponse keyImageResponse=formFileUpload(projectForm,keyImage,Const.FORM_IMAGE_TYPE_KEYIAMGE);
                if (!keyImageResponse.isSuccess()){
                    model.addAttribute("msg",keyImageResponse.getMsg());
                    return "error";
                }
            ServiceResponse response=projectService.addProject(projectForm,userVO);
            if (response.isSuccess()){
                return "success";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }

        }
        model.addAttribute("msg","你没有权限添加项目");
        return "error";

    }

    @RequestMapping(value = "addProjectPage.do")
    public String addProjectPage(HttpSession session,Model model){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            model.addAttribute("msg","用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO)||userService.isManager(userVO)){
            ServiceResponse response=userService.findAllUserByRoleId(Const.Role.user.getCode());
            if (response.isSuccess()){
                model.addAttribute("userList",response.getData());
                return "addProject";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }
        }
        model.addAttribute("msg","你没有权限添加项目");
        return "error";

    }
    @RequestMapping(value = "offline.do",method = RequestMethod.GET)
    public String offline(Integer projectId,HttpSession session,Model model) {
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO == null) {
            model.addAttribute("msg", "用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO) || userService.isManager(userVO)) {
            ServiceResponse response=projectService.projectOffline(projectId);
            if (response.isSuccess()){
                return "redirect:/project/findAll.do";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }
        }
        model.addAttribute("msg","你没有权限修改");
        return "error";
    }

    @RequestMapping(value = "online.do",method = RequestMethod.GET)
    public String online(Integer projectId,HttpSession session,Model model) {
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO == null) {
            model.addAttribute("msg", "用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO) || userService.isManager(userVO)) {
            ServiceResponse response=projectService.projectOnline(projectId);
            if (response.isSuccess()){
                return "redirect:/project/findAll.do";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }
        }
        model.addAttribute("msg","你没有权限修改");
        return "error";
    }

    @RequestMapping(value = "updateProjectPage.do",method = RequestMethod.GET)
    public String updateProjectPage(Integer projectId,Model model,HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO == null) {
            model.addAttribute("msg", "用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO) || userService.isManager(userVO)) {
            ServiceResponse response=projectService.findProjectById(projectId);
            if (response.isSuccess()){
                model.addAttribute("project",response.getData());
                ServiceResponse responseUser=userService.findAllUserByRoleId(Const.Role.user.getCode());
                if (!response.isSuccess()){
                    model.addAttribute("msg",responseUser.getMsg());
                    return "error";
                }
                model.addAttribute("userList",responseUser.getData());
                return "updateProject";
            }
            model.addAttribute("msg",response.getMsg());
            return "error";
        }
        model.addAttribute("msg","你没有权限修改");
        return "error";

    }

    @RequestMapping(value = "updateProject.do",method = RequestMethod.POST)
    public String updateProject(ProjectForm projectForm,HttpSession session,Model model,
                                @RequestParam(value = "newBanner", required = false) MultipartFile newBanner,
                                @RequestParam(value = "newKeyImage", required = false) MultipartFile newKeyImage){
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO == null) {
            model.addAttribute("msg", "用户登录超时，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO) || userService.isManager(userVO)) {
            ServiceResponse bannerResponse=formFileUpload(projectForm,newBanner,Const.FORM_IMAGE_TYPE_BANNER);
            if (!bannerResponse.isSuccess()){
                model.addAttribute("msg",bannerResponse.getMsg());
                return "error";
            }
            ServiceResponse keyImageResponse=formFileUpload(projectForm,newKeyImage,Const.FORM_IMAGE_TYPE_KEYIAMGE);
            if (!keyImageResponse.isSuccess()){
                model.addAttribute("msg",keyImageResponse.getMsg());
                return "error";
            }
            ServiceResponse response=projectService.updateProject(projectForm);
            if (response.isSuccess()){
                return "success";
            }else {
                model.addAttribute("msg",response.getMsg());
                return "error";
            }
        }
        model.addAttribute("msg","你没有权限修改");
        return "error";

    }
    @RequestMapping(value = "findAllProjectSize.do",method = RequestMethod.POST)
    public ServiceResponse findAllProjectSize(HttpSession session){
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userService.isAdmin(userVO)&&userService.isManager(userVO)){
            ServiceResponse response=projectService.findAllProjectSize();
            return response;
        }
        return ServiceResponse.createErrorMsg("你没有权限这么做");
    }

    private ServiceResponse formFileUpload(ProjectForm projectForm,MultipartFile file,String formImageType){
        if (file.isEmpty()){
            return ServiceResponse.createSuccess();
        }
        ServiceResponse response=fileUploadController.uploadFile(file);
        if (!response.isSuccess()){
            return response;
        }
        if (formImageType.equals(Const.FORM_IMAGE_TYPE_BANNER)){
            QiNiuPutRet bannerRet= (QiNiuPutRet) response.getData();
            projectForm.setProjectBannerHost(Const.QINIU_CDN_PREFIX+bannerRet.getKey());
            return ServiceResponse.createSuccess();
        }
        if (formImageType.equals(Const.FORM_IMAGE_TYPE_KEYIAMGE)){
            QiNiuPutRet keyImageRet= (QiNiuPutRet) response.getData();
            projectForm.setProjectKeyImageHost(Const.QINIU_CDN_PREFIX+keyImageRet.getKey());
            return ServiceResponse.createSuccess();
        }
        return ServiceResponse.createErrorMsg("图片类型错误");


    }

}
