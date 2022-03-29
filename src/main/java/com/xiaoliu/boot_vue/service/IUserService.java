package com.xiaoliu.boot_vue.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xiaoliu.boot_vue.controller.dto.UserDTO;
import com.xiaoliu.boot_vue.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 * @author 小刘
 * @since 2022-03-19
 */
public interface IUserService extends IService<User> {
        /**
        * 修改或者新增
        * @Param User
        */
        boolean addUp(User user);

        /**
        * 根据ID进行删除
        * @Param id 要删除的ID
        */
        boolean deletById(Integer id);

        /**
        * 批量删除
        * @Param ids 批量删除的ID
        */
        boolean deleteBatch(List<Integer> ids);

        /**
        * 查询所有信息
        */
        List<User> queryUser();

         /**
         *根据id查询
         */
        User queryById(Integer id);

        /**
        * 分页查询
        * @Param pageNum 页码
        * @Param pageSize 页面展示数
        */
        Page<User> queryPage(Integer pageNum,Integer pageSize,QueryWrapper<User> queryWrapper);

        /**
         * 登录
         * @param userDTO user前端接收类
         * @return
         */
        UserDTO login(UserDTO userDTO);

        /**
         * 注册用户
         * @param userDTO
         * @return
         */
        User register(UserDTO userDTO);
}
