package com.wanqing.labspring.service;

import com.wanqing.labspring.controller.dto.UserDTO;
import com.wanqing.labspring.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-02
 */
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

}
