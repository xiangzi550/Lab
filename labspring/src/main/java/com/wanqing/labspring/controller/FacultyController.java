package com.wanqing.labspring.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanqing.labspring.entity.User;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.wanqing.labspring.service.IFacultyService;
import com.wanqing.labspring.entity.Faculty;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/faculty")
        public class FacultyController {
    
@Resource
private IFacultyService facultyService;
//*新增或更新
@PostMapping
public boolean save(@RequestBody Faculty faculty){
        return facultyService.saveOrUpdate(faculty);
        }
@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id){
        return facultyService.removeById(id);
        }
@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> id){
        return facultyService.removeBatchByIds(id);
        }
@GetMapping
public List<Faculty> findAll(){
        return facultyService.list();
        }
@GetMapping("/{id}")
public Faculty findOne(@PathVariable Integer id) {
        return facultyService.getById(id);
        }

@GetMapping("/page")
public Page<Faculty> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String id,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String responsibler) {

        QueryWrapper<Faculty> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!"".equals(id)) {
                queryWrapper.like("id", id);
        }
        if(!"".equals(name)) {
                queryWrapper.like("name",name);
        }
        if(!"".equals(responsibler)) {
                queryWrapper.like("responsibler",responsibler);
        }
        return facultyService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }
        //*导出接口
        @GetMapping("/export")
        public void export(HttpServletResponse response) throws Exception {
//                @RequestMapping("/selectByWrapper2")
//                public List<SysUser> selectByWrapper2(){
//                        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//                        queryWrapper.select(SysUser.class, info ->!info.getColumn().equals("id") && !info.getColumn().equals("email"));//查询指定某字段以外的数据
//                        List<SysUser> sysUsers=sysUserService.list(queryWrapper);
//                        return sysUsers;
//                }

                //从数据库查询出所有的数据
                List<Faculty> list = facultyService.list();
                //查询AAA字段之外的属性
//                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//                        queryWrapper.select(User.class, info ->!info.getColumn().equals("AAA") );//查询指定某字段以外的数据
//                        List<User> list=userService.list(queryWrapper);
                // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
                // 在内存操作，写出到浏览器
                ExcelWriter writer = ExcelUtil.getWriter(true);
                //自定义标题别名
                writer.addHeaderAlias("id", "学院编号");
                writer.addHeaderAlias("name", "学院名");
                writer.addHeaderAlias("building", "楼号");
                writer.addHeaderAlias("num", "实验室数量");
                writer.addHeaderAlias("responsibler", "负责人");
                writer.addHeaderAlias("tel", "联系方式");
                writer.addHeaderAlias("createTime", "创建时间");

                // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
                writer.write(list, true);

                // 设置浏览器响应的格式
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
                String fileName = URLEncoder.encode("学院信息", "UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

                ServletOutputStream out = response.getOutputStream();
                writer.flush(out, true);
                out.close();
                writer.close();

        }
        /**
         * excel 导入
         * @param file
         * @throws Exception
         */
        @PostMapping("/import")
        public Boolean imp(MultipartFile file) throws Exception {
                InputStream inputStream = file.getInputStream();
                ExcelReader reader = ExcelUtil.getReader(inputStream);
//                // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//                List<User> list = reader.readAll(User.class);
//                System.out.println(list);
                // 方式2：忽略表头的中文，直接读取表的内容
                List<List<Object>> list = reader.read(1);
                List<Faculty> faculties = CollUtil.newArrayList();
                for (List<Object> row : list) {
                        Faculty faculty =new Faculty();
                        faculty.setId(row.get(0).toString());
                        faculty.setName(row.get(1).toString());
                        faculty.setBuilding(Integer.valueOf(row.get(2).toString()));
                        faculty.setNum(Integer.valueOf(row.get(3).toString()));
                        faculty.setResponsibler(row.get(4).toString());
                        faculty.setTel(String.valueOf(Integer.valueOf(row.get(5).toString())));
                        faculties.add(faculty);
                }
                facultyService.saveBatch(faculties);
                return true;
        }
        }

