package com.a15;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;

/**
 * @author gaoyang
 * create on 2022/6/7
 */
public class A15 {

   /* *//**
     * 切面
     *//*
    @Aspect
    static class MyAspect{

        @Before("execution(* foo())")
        public void before(){
            System.out.println("前置通知");
        }

        @After("execution(* foo())")
        public void after(){
            System.out.println("后置通知");
        }
    }
*/
    public static void main(String[] args) {
         /*
            两个切面概念
            aspect =
                通知1(advice) +  切点1(pointcut)
                通知2(advice) +  切点2(pointcut)
                通知3(advice) +  切点3(pointcut)
                ...
            advisor = 更细粒度的切面，包含一个通知和切点
         */
        //1.切点备好
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");
        //2.备好通知
        MethodInterceptor advice = invocation -> {
            System.out.println("before");
            Object result = invocation.proceed();
            System.out.println("after");
            return result;
        };
        //3.备好切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

        /**
         4. 创建代理
             a. proxyTargetClass = false, 目标实现了接口, 用 jdk 实现
             b. proxyTargetClass = false,  目标没有实现接口, 用 cglib 实现
             c. proxyTargetClass = true, 总是使用 cglib 实现
         */
        Target1 target1 = new Target1();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target1);
        factory.addAdvisor(advisor);
        factory.setInterfaces(target1.getClass().getInterfaces());
        factory.setProxyTargetClass(false);
        I1 proxy = (I1)factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.bar();
        proxy.foo();

    }

    interface I1 {
        void foo();

        void bar();
    }

    static class Target1 implements I1 {
        public void foo() {
            System.out.println("target1 foo");
        }

        public void bar() {
            System.out.println("target1 bar");
        }
    }

    static class Target2 {
        public void foo() {
            System.out.println("target2 foo");
        }

        public void bar() {
            System.out.println("target2 bar");
        }
    }
}
