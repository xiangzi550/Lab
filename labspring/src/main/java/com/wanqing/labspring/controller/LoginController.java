package com.wanqing.labspring.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.util.List;

import com.wanqing.labspring.service.ILoginService;
import com.wanqing.labspring.entity.Login;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/login")
        public class LoginController {
    
@Resource
private ILoginService loginService;
//*新增或更新
@PostMapping
public boolean save(@RequestBody Login login){
        return loginService.saveOrUpdate(login);
        }
@DeleteMapping("/{username}")
public Boolean delete(@PathVariable String username){
        return loginService.removeById(username);
        }
@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> usernames){
        return loginService.removeBatchByIds(usernames);
        }
@GetMapping
public List<Login> findAll(){
        return loginService.list();
        }
@GetMapping("/{username}")
public Login findOne(@PathVariable Integer username) {
        return loginService.getById(username);
        }
        @PostMapping("/login")
        public boolean login(@RequestBody Login login)
        {
               String username=login.getUsername();
               String password=login.getPassword();
               if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
                       return false;
               }
                return loginService.login(login);
        }
        }

