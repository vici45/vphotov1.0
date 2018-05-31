package com.zomo.vphoto.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.zomo.vphoto.DTO.QiNiuPutRet;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.service.IProjectDetailService;
import com.zomo.vphoto.service.IQiNiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    private Gson gson;

    @RequestMapping(value = "projectDetailFileupload.do")
    @ResponseBody
    public ServiceResponse projectDetailFileupload(@RequestParam(value = "uploadFile",required = false)MultipartFile file,
                                  @RequestParam(value = "projectId") Integer projectId){
        if (file.isEmpty()){
            return ServiceResponse.createErrorMsg("文件为空");
        }
        String originFileName=file.getOriginalFilename();
        try {
            InputStream inputStream=file.getInputStream();
            Response response=qiNiuService.fileUpload(inputStream);
            if (response.isOK()){
                QiNiuPutRet ret=gson.fromJson(response.bodyString(),QiNiuPutRet.class);
                ServiceResponse serviceResponse=projectDetailService.addDetailByProjectId(projectId,ret);
                if (!serviceResponse.isSuccess()){
                    return serviceResponse;
                }
                return ServiceResponse.createSuccess();
            }else {
                return ServiceResponse.createError(response.statusCode,response.getInfo());
            }
        }catch (QiniuException e) {
            Response response=e.response;
            return  ServiceResponse.createError(response.statusCode,response.getInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResponse.createErrorMsg("ioException");
        }

    }


}
