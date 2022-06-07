package com;


import org.springframework.context.ApplicationEvent;

/**
 * @author gaoyang
 * create on 2022/5/25
 */
public class UserRegisteredEvent extends ApplicationEvent {

    public UserRegisteredEvent(Object source){
        super(source);
    }
}
