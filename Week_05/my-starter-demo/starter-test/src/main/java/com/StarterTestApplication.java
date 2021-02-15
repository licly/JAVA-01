package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/8
 */

@SpringBootApplication
// 通过注解引入自定义starter模块的config类
@EnabledDiscoverStudent
public class StarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterTestApplication.class);
    }

    /**
     * 测试自定义Starter是否自动注入
     * @param student
     */
    @Autowired
    public void printStudentInfo(Student student) {
        System.out.println(student);
    }
}
