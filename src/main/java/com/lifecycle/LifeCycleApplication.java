package com.lifecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author gaoyang
 * create on 2022/5/26
 */
@SpringBootApplication
public class LifeCycleApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LifeCycleApplication.class, args);
        context.close();
    }
}
