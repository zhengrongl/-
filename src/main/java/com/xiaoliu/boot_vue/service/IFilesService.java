package com.xiaoliu.boot_vue.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoliu.boot_vue.entity.Files;
import com.xiaoliu.boot_vue.entity.User;

import java.util.List;


public interface IFilesService extends IService<Files> {
    /**
     * 文件上传信息
     * @param files 上传的文件信息
     * @return
     */
    boolean addFile(Files files);

    /**
     * 分页查询
     * @Param pageNum 页码
     * @Param pageSize 页面展示数
     */
    Page<Files> queryPage(Integer pageNum, Integer pageSize, QueryWrapper<Files> queryWrapper);

    /**
     * 根据ID进行删除
     * @Param id 要删除的ID
     */
    boolean deletById(Files files);

    /**
     * 批量删除
     * @Param ids 批量删除的ID
     */
    boolean deleteBatch(List<Integer> ids,QueryWrapper<Files> wrapper);

    /**
     * 查询所有信息
     */
    List<Files> queryFiles();

    /**
     *根据id查询
     */
    Files queryById(Integer id);

    /**
     * 更新启用
     * @Param User
     */
    boolean updateEnable(Files files);
}
