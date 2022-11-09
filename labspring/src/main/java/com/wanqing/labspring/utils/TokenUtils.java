package com.wanqing.labspring.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wanqing.labspring.entity.User;
import com.wanqing.labspring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        staticUserService =userService;
    }
    /**
    * 生成token
    * @return
    * */
    public static String genToken(String username,String sign){
        return JWT.create().withAudience(username) // 将 username 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //五分钟后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String username = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(username);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    }

