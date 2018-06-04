package com.zomo.vphoto.repository;

import com.zomo.vphoto.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    List<User> findByRoleId(Integer roleId);

    User findByName(String name);
}
