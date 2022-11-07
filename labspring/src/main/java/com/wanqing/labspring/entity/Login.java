/*
* 在这里创建定义实体类
* */
package com.wanqing.labspring.entity;

import lombok.Data;

@Data
//*登陆类
public class Login {
    //*用户名
    private Integer username;
    //*登陆密码
    private String password;
}
