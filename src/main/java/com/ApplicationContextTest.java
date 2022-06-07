package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author gaoyang
 * create on 2022/5/25
 */
public class ApplicationContextTest {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextTest.class);
    public static void main(String[] args) {
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
        testAnnotationApplicationContext();
    }


    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    private static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("D:\\Work_workSpaces\\springPractice\\src\\main\\resources\\b01.xml");
        String[] beanDefinitionNames = fileSystemXmlApplicationContext.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
        System.out.println(fileSystemXmlApplicationContext.getBean(Bean2.class).getBean1());
    }

    private static void testAnnotationApplicationContext(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }
    static class Bean1{

    }

    static class Bean2{
        private Bean1 bean1;

        public void setBean1(Bean1 bean1){
            this.bean1 = bean1;
        }

        public Bean1 getBean1(){
            return bean1;
        }
    }
}
