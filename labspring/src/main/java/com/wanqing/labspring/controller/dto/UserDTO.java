package com.wanqing.labspring.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
//*接受前端登录请求
public class UserDTO {
    private String username;
    private String password;
    private String name;
}
