package com;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;


/**
 * @author gaoyang
 * create on 2022/5/24
 */

@SpringBootApplication
public class SpringPracticeApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringPracticeApplication.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {

        ConfigurableApplicationContext context = SpringApplication.run(SpringPracticeApplication.class, args);


        /**
         * beanfactory获取单例的bean
         */
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                .forEach(e ->
                        System.out.println(e.getKey() + "=" + e.getValue()));

        /**
         * ApplicationContext 比 beanfactory
         * 国际化支持
         * 资源
         * 发布事件对象
         * 环境信息
         */
        //获取国际化支持
       /* String hi = context.getMessage("hi", null, Locale.CHINESE);
        System.out.println(hi);*/

        //对资源获取
        Resource[] resources = context.getResources("classpath:application.properties");
        for (Resource resource : resources){
            System.out.println(resource);
        }

        Resource[] factoryes = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : factoryes){
            System.out.println(resource);
        }

//



        //获取环境信息
        System.out.println(context.getEnvironment().getProperty("java_home"));

        //发送事件
        context.publishEvent(new UserRegisteredEvent(context));

    }
}
