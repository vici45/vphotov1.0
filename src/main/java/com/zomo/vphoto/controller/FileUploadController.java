package com.zomo.vphoto.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.zomo.vphoto.DTO.QiNiuPutRet;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.service.IProjectDetailService;
import com.zomo.vphoto.service.IProjectService;
import com.zomo.vphoto.service.IQiNiuService;
import com.zomo.vphoto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/upload/")
public class FileUploadController {
    @Autowired
    private IQiNiuService qiNiuService;
    @Autowired
    private IProjectDetailService projectDetailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private Gson gson;

    @RequestMapping(value = "projectDetailFileuploadPage.do")
    public String projectDetailFileuploadPage(HttpSession session, Model model, Integer projectId) {
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO == null) {
            model.addAttribute("msg", "用户登录信息过期，请重新登录");
            return "error";
        }
        if (userService.isAdmin(userVO) || userService.isManager(userVO)) {
            model.addAttribute("projectId", projectId);
            return "file";
        }
        ServiceResponse response = projectService.checkProjectPrivilege(projectId, userVO.getId());
        if (response.isSuccess()) {
            model.addAttribute("projectId",projectId);
            return "file";
        }
        model.addAttribute("msg",response.getMsg());
        return "error";
    }

    @RequestMapping(value = "projectDetailFileupload.do")
    @ResponseBody
    public ServiceResponse projectDetailFileupload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                                   @RequestParam(value = "projectId") Integer projectId,
                                                   HttpSession session) {
        UserVO userVO= (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            return ServiceResponse.createErrorMsg("用户登录信息过期,请重新登录");
        }
        if (!(userService.isManager(userVO)||userService.isAdmin(userVO))){
            ServiceResponse response=projectService.checkProjectPrivilege(projectId,userVO.getId());
            if (!response.isSuccess()){
                return ServiceResponse.createErrorMsg("你没有权限上传照片");
            }
        }
        if (file.isEmpty()) {
            return ServiceResponse.createErrorMsg("文件为空");
        }
        String originFileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            Response response = qiNiuService.fileUpload(inputStream,originFileName,projectId);
            if (response.isOK()) {
                QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                ServiceResponse serviceResponse = projectDetailService.addDetailByProjectId(projectId, ret);
                if (!serviceResponse.isSuccess()) {
                    return serviceResponse;
                }
                return ServiceResponse.createSuccess();
            } else {
                return ServiceResponse.createError(response.statusCode, response.getInfo());
            }
        } catch (QiniuException e) {
            Response response = e.response;
            return ServiceResponse.createError(response.statusCode, response.getInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResponse.createErrorMsg("ioException");
        }

    }
    public ServiceResponse uploadFile(MultipartFile file){
        if (file.isEmpty()){
            return ServiceResponse.createErrorMsg("上传文件为空");
        }
        String originFileName=file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            Response response = qiNiuService.fileUpload(inputStream,originFileName);
            if (response.isOK()) {
                QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                return ServiceResponse.createSuccess(ret);
            } else {
                return ServiceResponse.createError(response.statusCode, response.getInfo());
            }
        } catch (QiniuException e) {
            Response response = e.response;
            return ServiceResponse.createError(response.statusCode, response.getInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResponse.createErrorMsg("ioException");
        }
    }

}
