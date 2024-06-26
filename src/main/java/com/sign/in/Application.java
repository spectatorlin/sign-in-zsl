package com.sign.in;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: Application
 * @Description: TODO
 * @date 2024/3/28 13:48
 */
@SpringBootApplication
@MapperScan(value = "com.sign.in.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
