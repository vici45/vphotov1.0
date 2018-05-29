package com.zomo.vphoto.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String projectName;
    
    private String projectSit;
    
    private String projectBannerHost;

    private String projectKeyImageHost;

    private String projectContent;

    private String projectContentDetail;

    private Integer projectCreateUserId;

    private Integer projectStatus;

    private Date projectCreateTime;

    private Date projectUpdateTime;

    private Date projectTime;
}
