package com.zomo.vphoto.common.interceptor;

import com.zomo.vphoto.VO.UserVO;
import com.zomo.vphoto.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("--------------------------开始拦截请求-------------------------------");
        UserVO userVO= (UserVO) request.getSession().getAttribute(Const.CURRENT_USER);
        if (userVO==null){
            PrintWriter pw=response.getWriter();
            pw.write("please login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
