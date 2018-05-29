package com.zomo.vphoto.service.imp;

import com.zomo.vphoto.form.UserForm;
import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import com.zomo.vphoto.common.ServiceResponse;
import com.zomo.vphoto.entity.User;
import com.zomo.vphoto.repository.UserRepository;
import com.zomo.vphoto.service.IUserService;
import com.zomo.vphoto.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ServiceResponse.createError();
    }

    @Override
    public ServiceResponse registerManager(UserForm userForm) {

        ServiceResponse response=checkedUsername(userForm.getUsername());
        if (!response.isSuccess()){
            return ServiceResponse.createErrorMsg("用户名已被占用请重新尝试");
        }
        User user=new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(MD5Util.MD5EncodeUtf8("123456"));
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
            return ServiceResponse.createErrorMsg("用户名已被占用请重新尝试");
        }
        User user=new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(MD5Util.MD5EncodeUtf8("123456"));
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
}
