package org.charlie;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/20
 */
@SpringBootApplication
@EnableDubbo
public class UserbApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserbApplication.class, args);
    }
}
