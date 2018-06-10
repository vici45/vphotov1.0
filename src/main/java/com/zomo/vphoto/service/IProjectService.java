package com.zomo.vphoto.service;

import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.form.ProjectForm;

public interface IProjectService {
    ServiceResponse findAllProject();
    ServiceResponse findProjectById(Integer projectId);
    ServiceResponse addProject(ProjectForm projectForm, UserVO userVO);
    ServiceResponse findProjectByRetoucherId(UserVO userVO);
    ServiceResponse projectOffline(Integer projectId);
    ServiceResponse projectOnline(Integer projectId);
    ServiceResponse updateProject(ProjectForm projectForm);
    ServiceResponse checkProjectPrivilege(Integer projectId,Integer userId);
    ServiceResponse findAllProjectSize();
}
