package com.zomo.vphoto.service.imp;

import com.google.common.collect.Lists;
import com.zomo.vphoto.form.UserForm;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.entity.User;
import com.zomo.vphoto.repository.UserRepository;
import com.zomo.vphoto.service.IUserService;
import com.zomo.vphoto.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 登录功能 用户名 密码校验 DM5加密
     * @param username
     * @param password
     * @return serviceResponse<UserVo>
     */
    @Override
    public ServiceResponse login(String username, String password) {

        String md5Password=MD5Util.MD5EncodeUtf8(password);

        User user=userRepository.findByUsernameAndPassword(username,md5Password);
        if (user==null){
            return ServiceResponse.createErrorMsg("用户名或密码错误");
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ServiceResponse.createSuccess(userVO);
    }

    @Override
    public ServiceResponse checkedUsername(String username) {
        User user=userRepository.findByUsername(username);
        if (user==null){
            return ServiceResponse.createSuccess();
        }
        return ServiceResponse.createErrorMsg("用户名已被占用请重新尝试");
    }

    @Override
    public ServiceResponse registerManager(UserForm userForm) {

        ServiceResponse response=checkedUsername(userForm.getUsername());
        if (!response.isSuccess()){
            return response;
        }
        response=checkedName(userForm.getName());
        if (!response.isSuccess()){
            return response;
        }
        User user=new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(MD5Util.MD5EncodeUtf8("12345678"));
        user.setRoleId(Const.Role.MANAGER.getCode());
        user.setName(userForm.getName());
        User addUser=userRepository.save(user);
        if (addUser==null){
            return ServiceResponse.createErrorMsg("注册用户失败，请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse registerUser(UserForm userForm) {
        ServiceResponse response=checkedUsername(userForm.getUsername());
        if (!response.isSuccess()){
            return response;
        }
        response=checkedName(userForm.getName());
        if (!response.isSuccess()){
            return response;
        }
        User user=new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(MD5Util.MD5EncodeUtf8("12345678"));
        user.setRoleId(Const.Role.user.getCode());
        user.setName(userForm.getName());
        User addUser=userRepository.save(user);
        if (addUser==null){
            return ServiceResponse.createErrorMsg("注册用户失败，请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }

    /**
     * 判断用户是否是admin
     * @param userVO
     * @return
     */
    @Override
    public boolean isAdmin(UserVO userVO) {
        if (userVO.getRoleId()==Const.Role.ADMIN.getCode()){
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否是manager
     * @param userVO
     * @return
     */
    @Override
    public boolean isManager(UserVO userVO) {
        if (userVO.getRoleId()==Const.Role.MANAGER.getCode()){
            return true;
        }
        return false;
    }

    @Override
    public ServiceResponse resetPassword(UserForm userForm, UserVO userVO) {
        String username=userVO.getUsername();
        User user=userRepository.findByUsername(username);
        if (user==null){
            return ServiceResponse.createErrorMsg("用户信息错误请重新登录");
        }
        String passwordQuery=user.getPassword();
        String passwordInput=MD5Util.MD5EncodeUtf8(userForm.getPassword());
        if (!passwordQuery.equals(passwordInput)){
            return ServiceResponse.createErrorMsg("旧密码输入错误请重新尝试");
        }
        String passwordNewMd5=MD5Util.MD5EncodeUtf8(userForm.getResetPassword());
        user.setPassword(passwordNewMd5);
        User updateUser=userRepository.save(user);
        if (updateUser==null){
            return ServiceResponse.createErrorMsg("密码修改失败请重新尝试");
        }
        return ServiceResponse.createSuccess();
    }

    @Override
    public ServiceResponse findAllUserByRoleId(Integer roleId) {
        List<User> userList=userRepository.findByRoleId(roleId);
        if (userList.size()<=0){
            return ServiceResponse.createErrorMsg("角色错误");
        }
        List<UserVO> userVOList=Lists.newArrayList();
        for (User user : userList) {
            UserVO userVO=new UserVO();
            BeanUtils.copyProperties(user,userVO);
            userVOList.add(userVO);
        }
        return ServiceResponse.createSuccess(userVOList);
    }

    @Override
    public ServiceResponse checkedName(String name) {
        if (StringUtils.isEmpty(name)){
            return ServiceResponse.createErrorMsg("用户名为空验证错误");
        }
        User user=userRepository.findByName(name);
        if (user==null){
            return ServiceResponse.createSuccess();
        }
        return ServiceResponse.createErrorMsg("用户姓名已被注册，请更换");
    }
}
