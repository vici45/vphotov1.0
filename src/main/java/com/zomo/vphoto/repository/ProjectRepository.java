package com.zomo.vphoto.repository;

import com.zomo.vphoto.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    List<Project> findByProjectRetoucherId(Integer retoucherId);
    Project findByIdAndProjectRetoucherId(Integer projectId,Integer projectRetoucherId);
    @Transactional
    @Modifying
    @Query("update Project p set p.projectCodeHost = ?2 where p.id = ?1")
    Integer updateCodeHostById(Integer id,String projectCodeHost);
}
