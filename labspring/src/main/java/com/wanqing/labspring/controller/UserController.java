package com.wanqing.labspring.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanqing.labspring.common.Constants;
import com.wanqing.labspring.common.Result;
import com.wanqing.labspring.controller.dto.UserDTO;
import com.wanqing.labspring.entity.Usered;
import com.wanqing.labspring.service.IUseredService;
import com.wanqing.labspring.utils.TokenUtils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.wanqing.labspring.service.IUserService;
import com.wanqing.labspring.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
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

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    //*新增或更新
    @PostMapping
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    @DeleteMapping("/{username}")
    public Result delete(@PathVariable Integer username) {
//        insert into insertTest select * frominsertTest2;
//       useredService.saveOrUpdate(usered);
        return Result.success(userService.removeById(username));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> usernames) {
        return Result.success(userService.removeBatchByIds(usernames));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list()) ;
    }

    @GetMapping("/{username}")
    public Result findOne(@PathVariable Integer username) {
        return Result.success(userService.getById(username)) ;
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String level) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(level)) {
            queryWrapper.like("level", level);
        }
        User currentUser= TokenUtils.getCurrentUser();
        System.out.println("++++++++++++++++++++++++++++++++++"+currentUser.getName());
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));

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
        List<User> list = userService.list();
        //查询AAA字段之外的属性
//                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//                        queryWrapper.select(User.class, info ->!info.getColumn().equals("AAA") );//查询指定某字段以外的数据
//                        List<User> list=userService.list(queryWrapper);
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("tel", "电话");
        writer.addHeaderAlias("position", "职位");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("idcard", "证件号");
        writer.addHeaderAlias("level", "权限等级");
        writer.addHeaderAlias("createTime", "创建时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("人员信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//                // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//                List<User> list = reader.readAll(User.class);
//                System.out.println(list);
        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setName(row.get(1).toString());
            user.setGender(row.get(2).toString());
            user.setTel(row.get(3).toString());
            user.setPosition(row.get(4).toString());
            user.setAge(Integer.valueOf(row.get(5).toString()));
            user.setIdcard(row.get(6).toString());
            user.setLevel(row.get(7).toString());
            users.add(user);
        }
        userService.saveBatch(users);
        return Result.success(true);
    }


}