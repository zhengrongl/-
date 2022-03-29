package com.xiaoliu.boot_vue.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.boot_vue.common.Constants;
import com.xiaoliu.boot_vue.common.Result;
import com.xiaoliu.boot_vue.controller.dto.UserDTO;
import com.xiaoliu.boot_vue.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.xiaoliu.boot_vue.service.IUserService;
import com.xiaoliu.boot_vue.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小刘
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
       @Resource
       private IUserService userService;

       /*
       登录验证
        */
       @PostMapping("/login")
       public Result login(@RequestBody UserDTO userDTO){
           String username = userDTO.getUsername();
           String password = userDTO.getPassword();
           if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
               return Result.error(Constants.CODE_400,"参数错误");
           }
           UserDTO dto = userService.login(userDTO);
           return Result.success(dto);
       }
       /*
       注册用户
        */
       @PostMapping("/register")
       public Result register(@RequestBody UserDTO userDTO) {
           String username = userDTO.getUsername();
           String password = userDTO.getPassword();
           String nickname = userDTO.getNickname();
           if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname)){
               return Result.error(Constants.CODE_400,"参数错误");
           }
        return Result.success(userService.register(userDTO));
    }

       /*
       新增或者修改
        */
       @PostMapping("/save")
       public Result save(@RequestBody User user) {
           return Result.success(userService.addUp(user));
        }

        /*
        根据ID删除
         */
       @DeleteMapping("/delete/{id}")
       public Result delete(@PathVariable Integer id) {
           return Result.success(userService.deletById(id));
        }

        /*
        批量删除
        */
       @PostMapping("/delete/Batch")
       public Result deleteBatch(@RequestBody List<Integer> ids){
           return Result.success(userService.deleteBatch(ids));
        }

        /*
        查询所有信息
         */
       @GetMapping
       public Result findAll() {
           return Result.success(userService.queryUser());
        }

        /*
        根据id查询
         */
       @GetMapping("queryId/{id}")
       public Result findOne(@PathVariable Integer id) {
           return Result.success(userService.queryById(id));
        }

        /*
        根据用户名查询获取信息
         */
       @GetMapping("/username/{username}")
       public Result queryUsername(@PathVariable String username) {
           QueryWrapper<User> queryWrapper=new QueryWrapper<>();
           queryWrapper.eq("username",username);
           return Result.success(userService.getOne(queryWrapper));
    }

        /*
        分页查询
         */
       @GetMapping("/page")
       @ResponseBody
       public Result findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String email,
                                  @RequestParam(defaultValue = "") String address,
                                 QueryWrapper<User> queryWrapper) {

           queryWrapper = new QueryWrapper<>();
           if (StringUtils.isNotBlank(username)) {
               queryWrapper.like("username", username);
           }
           if (StringUtils.isNotBlank(email)) {
               queryWrapper.like("email", email);
           }
           if (StringUtils.isNotBlank(address)) {
               queryWrapper.like("address", address);
           }
           //获取当前用户信息
           User currentUser = TokenUtils.getCurrentUser();
           System.out.println(currentUser.getNickname());

           queryWrapper.orderByDesc("id");
           return Result.success(userService.queryPage(pageNum, pageSize,queryWrapper));
        }

        /*
        excel导出
         */
        @GetMapping("/export")
        public void export(HttpServletResponse response) throws IOException {
           //从数据库查询出所有的数据
            List<User> list = userService.queryUser();
            //通过工具类创建writer写在磁盘路径
            //在内存操作，写出浏览器
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //自定义标题别名
            writer.addHeaderAlias("username","用户名");
            writer.addHeaderAlias("password","密码");
            writer.addHeaderAlias("nickname","昵称");
            writer.addHeaderAlias("email","邮箱");
            writer.addHeaderAlias("phone","电话");
            writer.addHeaderAlias("address","地址");
            writer.addHeaderAlias("createTime","创建时间");
            writer.addHeaderAlias("avatarUrl","头像");
            writer.addHeaderAlias("role","角色");

            //一次性写出list内的对象到excel,使用默认样式，强制输出标题
            writer.write(list,true);

            //设置浏览器响应的格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String fileName = URLEncoder.encode("用户信息", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();//输出流
            writer.flush(out,true);//刷新到输出流中
            //关闭资源
            out.close();
            writer.close();

        }
        /*
        excel导入
         */
        @PostMapping("/import")
        public Result imp(MultipartFile file) throws Exception {
            InputStream inputStream = file.getInputStream(); //获取文件输入流
            ExcelReader reader = ExcelUtil.getReader(inputStream);//转换成输出流
            List<User> list = reader.readAll(User.class); //保存给实体类
            return Result.success(userService.saveBatch(list)); //批量添加数据到数据库
        }

}

