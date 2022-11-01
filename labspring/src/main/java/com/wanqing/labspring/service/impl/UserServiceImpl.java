package com.wanqing.labspring.service.impl;

import com.wanqing.labspring.entity.User;
import com.wanqing.labspring.mapper.UserMapper;
import com.wanqing.labspring.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
