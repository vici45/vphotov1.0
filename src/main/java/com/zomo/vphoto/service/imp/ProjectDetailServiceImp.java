package com.zomo.vphoto.service.imp;

import com.zomo.vphoto.DTO.QiNiuPutRet;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.config.WebFileUploadConfig;
import com.zomo.vphoto.entity.ProjectDetail;
import com.zomo.vphoto.repository.ProjectDetailRepository;
import com.zomo.vphoto.service.IProjectDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProjectDetailServiceImp implements IProjectDetailService {
    @Autowired
    private ProjectDetailRepository projectDetailRepository;


    @Override
    public ServiceResponse addDetailByProjectId(Integer projectId, QiNiuPutRet ret) {
        String imageHost= ret.getKey();
        Integer fsize=ret.getFsize();
        BigDecimal b=new BigDecimal((float)fsize/1048576);
        String size=b.setScale(1,BigDecimal.ROUND_HALF_UP).toString()+"M";
        ProjectDetail projectDetail=new ProjectDetail();
        projectDetail.setProjectId(projectId);
        projectDetail.setImageHost(imageHost);
        projectDetail.setStatus(Const.DetailStatus.ONLINE.getCode());
        projectDetail.setSize(size);
        projectDetail=projectDetailRepository.save(projectDetail);
        if (projectDetail==null){
            return ServiceResponse.createErrorMsg("上传文件失败，请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }
}
