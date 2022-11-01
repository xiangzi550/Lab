package com.wanqing.labspring.controller;

import com.wanqing.labspring.entity.Login;
import com.wanqing.labspring.mapper.LoginMapper;
import com.wanqing.labspring.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private LoginService loginService;
    @PostMapping()
    public Integer save(@RequestBody Login login)
    {
        return loginService.save(login);
    }
    @GetMapping()
    public List<Login> index() {
        return loginMapper.findAll();
    }
}
