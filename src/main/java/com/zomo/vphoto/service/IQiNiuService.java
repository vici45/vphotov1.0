package com.zomo.vphoto.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

public interface IQiNiuService {

    Response fileUpload(File file) throws QiniuException;

    Response fileUpload(InputStream inputStream,String originFileName) throws QiniuException;

    Response delete(String key) throws QiniuException;
}
