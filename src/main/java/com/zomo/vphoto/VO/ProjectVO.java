package com.zomo.vphoto.VO;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectVO {

    private Integer id;

    private String projectName;

    private String projectSit;

    private String projectContent;

    private String projectStatusName;

    private Integer projectStatus;

    private Date projectTime;

    private String projectCreateUserName;

    private String projectRetoucherName;

}
