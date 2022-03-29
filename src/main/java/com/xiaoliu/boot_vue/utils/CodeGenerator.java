package com.xiaoliu.boot_vue.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mp代码生成器
 * by 小刘
 * @since 2022-03-19
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }
    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/vue_oa?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("小刘") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\ideaDemo\\boot_vue\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xiaoliu.boot_vue") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\ideaDemo\\boot_vue\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();//添加Lombok
                    builder.controllerBuilder().enableHyphenStyle(). //开启驼峰转连注解
                            enableRestStyle();//开启@RestController注解
                    builder.addInclude("sys_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
