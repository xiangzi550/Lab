package com.wanqing.labspring.service;

import com.wanqing.labspring.entity.Login;
import com.wanqing.labspring.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;
    public int save(Login login){
        if (login.getUsername()==null){
            return loginMapper.update(login);
        } else {
            return loginMapper.insert(login);
        }
    }
}