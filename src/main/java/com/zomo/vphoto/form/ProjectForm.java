package com.zomo.vphoto.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class ProjectForm {

    private Integer id;
    @NotBlank(message = "项目名不能为空")
    private String projectName;

    @NotBlank(message = "项目地点不能为空")
    private String projectSit;

    private String projectBannerHost;

    private String projectKeyImageHost;

    @NotBlank(message = "项目概述不能为空")
    private String projectContent;

    @NotBlank(message = "项目详细详情不能为空")
    private String projectContentDetail;

    private Integer projectStatus;

    private Date projectTime;

    @NotNull(message = "项目修图师不能为空")
    private Integer projectRetoucherId;
}
