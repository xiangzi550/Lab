package com.wanqing.labspring.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.util.List;

import com.wanqing.labspring.service.IUseredService;
import com.wanqing.labspring.entity.Usered;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-02
 */
@RestController
@RequestMapping("/usered")
        public class UseredController {
    
@Resource
private IUseredService useredService;
//*新增或更新
@PostMapping
public boolean save(@RequestBody Usered usered){
        return useredService.saveOrUpdate(usered);
        }
@DeleteMapping("/{username}")
public Boolean delete(@PathVariable Integer username){
        return useredService.removeById(username);
        }
@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> usernames){
        return useredService.removeBatchByIds(usernames);
        }
@GetMapping
public List<Usered> findAll(){
        return useredService.list();
        }
@GetMapping("/{username}")
public Usered findOne(@PathVariable Integer username) {
        return useredService.getById(username);
        }

@GetMapping("/page")
public Page<Usered> findPage(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "") String level) {

        QueryWrapper<Usered> queryWrapper = new QueryWrapper<>();
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
        return useredService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }


        }

