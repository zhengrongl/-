package com.xiaoliu.boot_vue.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xiaoliu.boot_vue.entity.User;
import com.xiaoliu.boot_vue.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }
    /**
     * 生成Token
     * @param userID 用户id
     * @param sign 用户密码
     * @return
     */
    public static String genToken(String userID, String sign){
       return JWT.create().withAudience(userID) // 将 user id 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
        
    }

    /**
     * 获取当前登录的用户信息
     * @return user对象
     */
    public static User getCurrentUser(){
        try {
            //获取当前请求的Request
            HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            //获取请求头中的token
            String token = request.getHeader("token");
            if(!StrUtil.isBlank(token)) {
                //解析token
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(userId);
            }
        }catch (Exception e){
            return null;
        }
            return null;

    }
}
