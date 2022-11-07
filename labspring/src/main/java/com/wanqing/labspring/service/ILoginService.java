package com.wanqing.labspring.service;

import com.wanqing.labspring.entity.Login;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-05
 */
public interface ILoginService extends IService<Login> {

    boolean login(Login login);
}
