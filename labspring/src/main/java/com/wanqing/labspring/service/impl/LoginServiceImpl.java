package com.wanqing.labspring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanqing.labspring.entity.Login;
import com.wanqing.labspring.mapper.LoginMapper;
import com.wanqing.labspring.service.ILoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-05
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

    @Override
    public boolean login(Login login) {
        QueryWrapper<Login> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",login.getUsername());
        queryWrapper.eq("password",login.getPassword());
        try {
            Login one = getOne(queryWrapper);
            return one!=null;
        }catch  (Exception e){
        return false;}
    }
}
