package com.zomo.vphoto.service.imp;

import com.google.common.collect.Lists;
import com.zomo.vphoto.VO.ProjectVO;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.entity.Project;
import com.zomo.vphoto.entity.User;
import com.zomo.vphoto.form.ProjectForm;
import com.zomo.vphoto.repository.ProjectRepository;
import com.zomo.vphoto.repository.UserRepository;
import com.zomo.vphoto.service.IProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImp implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ServiceResponse findAllProject() {
        List<Project> projectList= (List<Project>) projectRepository.findAll();
        if (projectList.size()<=0){
            return ServiceResponse.createErrorMsg("目前没有项目");
        }
        ServiceResponse response=assembleProjectList2ProjectVoList(projectList);
        return response;
    }

    @Override
    public ServiceResponse findProjectById(Integer projectId) {
        Project project=projectRepository.findOne(projectId);
        if (project==null){
            return ServiceResponse.createErrorMsg("项目ID错误");
        }
        ProjectForm projectForm=new ProjectForm();
        BeanUtils.copyProperties(project,projectForm);
        return ServiceResponse.createSuccess(projectForm);
    }


    @Override
    public ServiceResponse addProject(ProjectForm projectForm, UserVO userVO) {
        Project project=new Project();
        BeanUtils.copyProperties(projectForm,project);
        project.setProjectCreateUserId(userVO.getId());
        project.setProjectStatus(Const.ProjectStatus.ONLINE.getCode());
        project=projectRepository.save(project);
        if (project==null){
            return ServiceResponse.createErrorMsg("添加项目失败请重试");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse findProjectByRetoucherId(UserVO userVO) {
        List<Project> projectList=projectRepository.findByProjectRetoucherId(userVO.getId());
        if (projectList.size()<=0){
            return ServiceResponse.createErrorMsg("没有可操作的项目");
        }
        ServiceResponse response=assembleProjectList2ProjectVoList(projectList);
        return response;
    }

    @Override
    public ServiceResponse projectOffline(Integer projectId) {
        Project project=projectRepository.findOne(projectId);
        if (project==null){
            return ServiceResponse.createErrorMsg("项目ID错误请重新尝试");
        }
        project.setProjectStatus(Const.ProjectStatus.OFFLINE.getCode());
        project=projectRepository.save(project);
        if (project==null){
            return ServiceResponse.createErrorMsg("修改项目状态错误请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse projectOnline(Integer projectId) {
        Project project=projectRepository.findOne(projectId);
        if (project==null){
            return ServiceResponse.createErrorMsg("项目ID错误请重新尝试");
        }
        project.setProjectStatus(Const.ProjectStatus.ONLINE.getCode());
        project=projectRepository.save(project);
        if (project==null){
            return ServiceResponse.createErrorMsg("修改项目状态错误请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    @Modifying
    public ServiceResponse updateProject(ProjectForm projectForm) {
        Project project=new Project();
        BeanUtils.copyProperties(projectForm,project);
        Project queryProject=projectRepository.findOne(projectForm.getId());
        if (queryProject==null){
            return ServiceResponse.createErrorMsg("项目不存在请重新尝试");
        }
        project.setProjectCreateUserId(queryProject.getProjectCreateUserId());
        project=projectRepository.save(project);
        if (project==null){
            return ServiceResponse.createErrorMsg("更新项目失败");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse checkProjectPrivilege(Integer projectId, Integer userId) {
        Project project=projectRepository.findByIdAndProjectRetoucherId(projectId,userId);
        if (project==null){
            return ServiceResponse.createErrorMsg("你没有该项目的权限");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse findAllProjectSize() {
        List<Project> projectList= (List<Project>) projectRepository.findAll();
        if (projectList.size()>=0){
            return ServiceResponse.createSuccess(projectList.size());
        }
        return ServiceResponse.createErrorMsg("查询错误");
    }

    private ServiceResponse assembleProjectList2ProjectVoList(List<Project> projectList){
        List<ProjectVO> projectVOList=Lists.newArrayList();
        for (Project project : projectList) {
            ProjectVO projectVO=new ProjectVO();
            BeanUtils.copyProperties(project,projectVO);
            User createProjectUser=userRepository.findOne(project.getProjectCreateUserId());
            if (createProjectUser==null){
                return ServiceResponse.createErrorMsg("内部错误，请重新尝试");
            }
            projectVO.setProjectCreateUserName(createProjectUser.getName());
            User retoucherUser=userRepository.findOne(project.getProjectRetoucherId());
            if (retoucherUser == null) {
                return ServiceResponse.createErrorMsg("内部错误,请重新尝试");
            }
            projectVO.setProjectRetoucherName(retoucherUser.getName());
            projectVO.setProjectStatusName(Const.ProjectStatus.getName(project.getProjectStatus()));
            projectVOList.add(projectVO);
        }
        return ServiceResponse.createSuccess(projectVOList);
    }

}
