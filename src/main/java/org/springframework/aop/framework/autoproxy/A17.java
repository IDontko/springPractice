package org.springframework.aop.framework.autoproxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;


public class A17 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("config", Config.class);
        //让@Bean生效
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class, new BeanDefinitionCustomizer[0]);

        context.registerBean("target1", Target1.class);
        context.refresh();

//        Target1 target1 = (Target1) context.getBean("target1");
//        target1.foo();
        AnnotationAwareAspectJAutoProxyCreator creator = (AnnotationAwareAspectJAutoProxyCreator)context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
     /*   List<Advisor> advisors = creator.findEligibleAdvisors(A17.Target2.class, "target2");
        for(Advisor advisor: advisors){
            System.out.println(advisor.toString());
        }*/
        Object o1 = creator.wrapIfNecessary(new A17.Target1(), "target1", "target1");
        System.out.println(o1.getClass());
        Object o2 = creator.wrapIfNecessary(new A17.Target2(), "target2", "target2");
        System.out.println(o2.getClass());
        ((A17.Target1)o1).foo();

//        for(String name: context.getBeanDefinitionNames()){
//            System.out.println(name);
//        }


    }

    static class Target1 {
        public void foo() {
            System.out.println("target1 foo");
        }
    }

    static class Target2 {
        public void bar() {
            System.out.println("target2 foo");
        }
    }

    @Aspect //高级切面类
//    Order放在类上设置spring的加载顺序。
    @Order(1)
    static class Aspect1 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect before");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect after");
        }
    }

    @Configuration
    static class Config {
        @Bean
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, advice3);
        }

        @Bean
        public MethodInterceptor advice3() {
            return invocation -> {
                System.out.println("advice3 aspect before");
                Object result = invocation.proceed();
                System.out.println(" advice3 aspect after");
                return result;
            };
        }
    }
}
