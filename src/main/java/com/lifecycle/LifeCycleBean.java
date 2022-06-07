package com.lifecycle;

import com.Component2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author gaoyang
 * create on 2022/5/26
 * bean的生命周期
 */
@Component
public class LifeCycleBean {

    private static final Logger log = LoggerFactory.getLogger(Component2.class);

    public LifeCycleBean(){
        log.debug("构造方法");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home){
        log.debug("依赖注入: {}", home);
    }

    @PostConstruct
    public void init(){
        log.debug("初始化");
    }

    @PreDestroy
    public void destroy(){
        log.debug("销毁");
    }
}
