package com.zomo.vphoto.service;

import com.zomo.vphoto.DTO.QiNiuPutRet;
import com.zomo.vphoto.common.ServiceResponse;

public interface IProjectDetailService {
    ServiceResponse addDetailByProjectId(Integer projectId, QiNiuPutRet ret);
}
