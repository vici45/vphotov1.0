package com.zomo.vphoto.repository;

import com.zomo.vphoto.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project,Integer> {
    List<Project> findByProjectRetoucherId(Integer retoucherId);
    Project findByIdAndProjectRetoucherId(Integer projectId,Integer projectRetoucherId);
}
