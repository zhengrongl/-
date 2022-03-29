package com.xiaoliu.boot_vue;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BootVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootVueApplication.class, args);
    }

}
