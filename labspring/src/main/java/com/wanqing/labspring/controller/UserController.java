package com.wanqing.labspring.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanqing.labspring.entity.Usered;
import com.wanqing.labspring.service.IUseredService;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.util.List;

import com.wanqing.labspring.service.IUserService;
import com.wanqing.labspring.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/user")
        public class UserController {

@Resource
private IUserService userService;
@Resource
private IUseredService useredService;
//*新增或更新
@PostMapping
public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);
        }
@DeleteMapping("/{username}")
public Boolean delete(@PathVariable Integer username){
//        insert into insertTest select * frominsertTest2;
//       useredService.saveOrUpdate(usered);
        return userService.removeById(username);
        }
@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> usernames){
        return userService.removeBatchByIds(usernames);
        }
@GetMapping
public List<User> findAll(){
        return userService.list();
        }
@GetMapping("/{username}")
public User findOne(@PathVariable Integer username) {
        return userService.getById(username);
        }

        @GetMapping("/page")
        public Page<User> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String level) {

                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.orderByDesc("create_time");
                if(!"".equals(username)) {
                        queryWrapper.like("username", username);
                }
                if(!"".equals(name)) {
                        queryWrapper.like("name",name);
                }
                if(!"".equals(level)) {
                        queryWrapper.like("level",level);
                }
                return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }


}