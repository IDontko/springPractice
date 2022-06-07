package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaoyang
 * create on 2022/5/25
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",  beanDefinition);
        for(String name : beanFactory.getBeanDefinitionNames()){
            System.out.println(name);
        }

        //beanFactory 添加后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        for(String name : beanFactory.getBeanDefinitionNames()){
            System.out.println(name);
        }

        //beanFactoryPostProcessor补充了一些bean的定义 beanDefinition
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()
                .forEach(beanFactoryPostProcessor -> {
                    beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
                });

        for(String name : beanFactory.getBeanDefinitionNames()){
            System.out.println(name);
        }


        //bean的后置处理器, 针对bean的生命周期各个阶段提供扩展
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory :: addBeanPostProcessor);

        //准备所有的单例
//        beanFactory.preInstantiateSingletons();
        System.out.println(">>>>>>>>>>>>>>>>>>>");
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);
        public Bean1(){
            log.debug("构造 bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){
            return bean2;
        }
    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);
        public Bean2(){
            log.debug("构造 bean2()");
        }
    }
}
