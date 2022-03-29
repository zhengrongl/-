package com.xiaoliu.boot_vue.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.boot_vue.common.Result;
import com.xiaoliu.boot_vue.entity.Files;
import com.xiaoliu.boot_vue.service.impl.FilesServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    //获取配置文件中指定的文件保存路径
    @Value("${files.upload.path}")
    private String fileuploadPath;


    @Resource
    private FilesServiceImpl filesService;


    /**
     *文件上传接口
     * @param file 上传进来的文件
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //获取文件原始名称
        String filename = file.getOriginalFilename();
        //获取文件类型,后缀
        String type = FileUtil.extName(filename);
        //获取文件大小
        long size = file.getSize();

        //定义一个文件唯一的标识码
        String fileUuid = IdUtil.fastSimpleUUID() + StrUtil.DOT + type; //获取uuid+.+文件后缀
        File uploadFilePath = new File(fileuploadPath + fileUuid);

        //判断文件是否存在，不存在则创建一个新的文件目录
        File uploadFile = uploadFilePath.getParentFile();
        if (!uploadFile.exists()){ //当文件不存在我们就创建这个文件
            uploadFile.mkdirs();
        }

        //获取md5
        String md5=SecureUtil.md5(file.getInputStream());
        String url; //上传到数据的url路径
        //通过对比md5避免重复上传相同内容的文件
        Files one = filesService.getFileMD5(md5);
            if (one!=null){
                url=one.getUrl();
            }else {
                //数据库中md5不存在把获取到的文件存储到磁盘目录去
                file.transferTo(uploadFilePath);
                //不存在则自动生成url
                url="http://localhost:9090/file/" + fileUuid;
            }



        //存储数据库
        Files files=new Files();
        files.setName(filename);
        files.setType(type);
        files.setSize(size/1024);
        files.setUrl(url);
        files.setMd5(md5);
        //添加
        filesService.addFile(files);
        return url;

    }

    /**
     * 文件下载接口
     * @param fileUuid 唯一标识码
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileuploadPath + fileUuid);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
        }

    /*
    分页查询
     */
    @GetMapping("/page")
    @ResponseBody
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }

        return Result.success(filesService.queryPage(pageNum, pageSize,queryWrapper));
    }

    /*
     根据ID删除
      */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = filesService.queryById(id);
        files.setDelete(true);
        filesService.deletById(files);
        return Result.success();
    }

    /*
    批量删除
    */
    @PostMapping("/delete/Batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.in("id",ids);
        filesService.deleteBatch(ids,wrapper);
        return Result.success();
    }

    /*
    查询所有信息
     */
    @GetMapping
    public Result findAll() {
        return Result.success(filesService.queryFiles());
    }

    /*
    根据id查询
     */
    @GetMapping("queryId/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(filesService.queryById(id));
    }

    /*
    新增或者修改
    */
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(filesService.updateEnable(files));
    }
}
