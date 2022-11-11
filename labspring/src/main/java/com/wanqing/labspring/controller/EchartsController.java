package com.wanqing.labspring.controller;

import cn.hutool.core.collection.CollUtil;
import com.wanqing.labspring.common.Result;
import com.wanqing.labspring.entity.Faculty;
import com.wanqing.labspring.entity.Lab;
import com.wanqing.labspring.entity.User;
import com.wanqing.labspring.service.IFacultyService;
import com.wanqing.labspring.service.ILabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private ILabService labService;
    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map =new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }
    @GetMapping("/labtype")
    public Result labtype(){
        List<Lab> list = labService.list();
        int type1=0;
        int type2=0;
        int type3=0;
        int type4=0;

        for (Lab lab : list) {
            String type=lab.getType();
            switch (type){
                case "教学型实验室": type1 +=1;break;
                case "校级实验室": type2 +=1;break;
                case "省级实验室": type3 +=1;break;
                case "国家级实验室": type4 +=1;break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(type1,type2,type3,type4));
    }
}
