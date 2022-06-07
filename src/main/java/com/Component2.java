package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gaoyang
 * create on 2022/5/24
 */
@Component
public class Component2 {

    private static final Logger log = LoggerFactory.getLogger(Component2.class);


    //接受事件
    @EventListener
    public void aa(UserRegisteredEvent event){
        log.debug("{}", event);
    }
}
