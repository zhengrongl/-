package com.xiaoliu.boot_vue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     *
     * @return
     */
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("标准接口")
                .apiInfo(apiInfo("Swagger API文档","v1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiaoliu.boot_vue.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建访问API的基本信息
     * 访问地址：http://ip:prot/swagger-ui.html
     * @param title 标题
     * @param version 版本号
     * @return
     */
    public ApiInfo apiInfo(String title,String version){
        Contact contact = new Contact("刘峥嵘", "https://gitee.com/liu-zhengrongJava/gitstudy/issues", "3100769003@qq.com");
        return new ApiInfo(
                title,
                "Vue+Boot后台管理系统",
                version,
                "更多请关注：https://gitee.com/liu-zhengrongJava/gitstudy/issues",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>()
        );
    }
}
