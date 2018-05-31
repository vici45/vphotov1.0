package com.zomo.vphoto.form;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectForm {

    private Integer id;

    private String projectName;

    private String projectSit;

    private String projectBannerHost;

    private String projectKeyImageHost;

    private String projectContent;

    private String projectContentDetail;

    private Integer projectStatus;

    private Date projectTime;

    private Integer projectRetoucherId;
}
