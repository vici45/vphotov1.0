package com.zomo.vphoto.service;

import com.zomo.vphoto.form.UserForm;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.ServiceResponse;

public interface IUserService {

    ServiceResponse login(String username,String password);

    ServiceResponse checkedUsername(String username);

    ServiceResponse registerManager(UserForm userForm);

    ServiceResponse registerUser(UserForm userForm);

    boolean isAdmin(UserVO userVO);

    boolean isManager(UserVO userVO);

    ServiceResponse resetPassword(UserForm userForm,UserVO userVO);

    ServiceResponse findAllUserByRoleId(Integer roleId);

}
