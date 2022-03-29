package com.xiaoliu.boot_vue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoliu.boot_vue.entity.Files;
import com.xiaoliu.boot_vue.mapper.FilesMapper;
import com.xiaoliu.boot_vue.service.IFilesService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {
    public boolean addFile(Files files){
        return save(files);
    }

    @Override
    public Page<Files> queryPage(Integer pageNum, Integer pageSize, QueryWrapper<Files> queryWrapper) {
        return page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    @Override
    public boolean deletById(Files files) {
        return updateById(files);
    }

    @Override
    public boolean deleteBatch(List<Integer> ids,QueryWrapper<Files> wrapper) {
        wrapper= new QueryWrapper<>();
        List<Files> list = list(wrapper);
        for (Files files : list) {
            files.setDelete(true);
            updateById(files);
        }
        return removeByIds(ids);
    }

    @Override
    public List<Files> queryFiles() {
        return list();
    }

    @Override
    public boolean updateEnable(Files files) {
        return updateById(files);
    }

    @Override
    public Files queryById(Integer id) {
        return getById(id);
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    public Files getFileMD5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        //可能查询出多条相同的MD5数据，我们只获取第一条MD5数据
        List<Files> list = list(queryWrapper);
        return list.size()==0? null : list.get(0);
    }
}
