/*
* 数据库查询的接口
* */
package com.wanqing.labspring.mapper;

import com.wanqing.labspring.entity.Login;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
//@Mapper
@Component
public interface LoginMapper {
//数据库查询出来的是一个list
    @Select("select * from login")
    List<Login> findAll();
    @Insert("insert into login(username,password) values (#{username},#{password})")
    int insert(Login login);

    int update(Login login);
}
