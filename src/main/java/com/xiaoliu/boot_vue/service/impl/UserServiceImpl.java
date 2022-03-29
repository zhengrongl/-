package com.xiaoliu.boot_vue.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.xiaoliu.boot_vue.common.Constants;
import com.xiaoliu.boot_vue.controller.dto.UserDTO;
import com.xiaoliu.boot_vue.entity.User;
import com.xiaoliu.boot_vue.exception.ServiceException;
import com.xiaoliu.boot_vue.mapper.UserMapper;
import com.xiaoliu.boot_vue.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoliu.boot_vue.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小刘
 * @since 2022-03-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG=Log.get();

    private User getUserInfo(UserDTO userDTO){
        //存储传入过来的数据
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); //从数据库查询出的用户信息
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
    @Override
    public User register(UserDTO userDTO) {
        User info = getUserInfo(userDTO);
        if (info==null){//判断是否存在用户，不存在则添加数据
            info=new User();
            BeanUtils.copyProperties(userDTO,info);
            save(info); //把copy完后的数据存到数据库
        }else {
            //自定义异常
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return info;
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one!=null) {
            //如果不为null则数据到userDTO
            BeanUtils.copyProperties(one,userDTO);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            //自定义异常
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }

        @Override
        public boolean addUp(User user){
            return saveOrUpdate(user);
        }
        @Override
        public boolean deletById(Integer id){
            return removeById(id);
        }
        @Override
        public boolean deleteBatch(List<Integer> ids){
            return removeByIds(ids);
        }
        @Override
        public List<User> queryUser(){
            return list();
        }
        @Override
        public User queryById(Integer id){
            return  getById(id);
        }
        @Override
        public Page<User> queryPage(Integer pageNum,Integer pageSize,QueryWrapper<User> queryWrapper){
            return page(new Page<>(pageNum, pageSize),queryWrapper);
        }
}
