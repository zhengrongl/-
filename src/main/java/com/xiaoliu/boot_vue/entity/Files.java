package com.xiaoliu.boot_vue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("sys_file")
@Data
public class Files {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("Id")
    private int id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private long size;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("文件md5")
    private String md5;

    @ApiModelProperty("是否删除")
    private boolean isDelete;

    @ApiModelProperty("是否禁用链接")
    private boolean enable;
}
