package com.zomo.vphoto.repository;

import com.zomo.vphoto.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

    User findByUsernameAndPassword(String username,String password);

    User findByUsername(String username);
}
