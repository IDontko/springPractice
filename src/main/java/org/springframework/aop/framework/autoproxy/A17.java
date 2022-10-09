package org.springframework.aop.framework.autoproxy;

import com.a13.Target;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class A17 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("config", Config.class);
        //让@Bean生效
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        for(String beanName : context.getBeanDefinitionNames()){
            System.out.println(beanName);
        }
        context.registerBean("target1", Target1.class);
        context.refresh();

  /*      Target1 target1 = (Target1) context.getBean("target1");
        target1.foo();*/

        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        //根据目标类型，找到合适的切面。
        List<Advisor> advisors = creator.findEligibleAdvisors(Target1.class, " target1");
        //一共有4个切面
        for(Advisor advisor: advisors){
            System.out.println(advisor.toString());
        }

        Object o1 = creator.wrapIfNecessary(new Target1(), "target1", "target1");
        System.out.println(o1.getClass());
        //target2没有合适的切面，不需要创建代理
        Object o2 = creator.wrapIfNecessary(new Target2(), "target2", "target2");
        System.out.println(o2.getClass());
        //代理对象，调用方法，会增强
        ((Target1) o1).foo();
        //调用目标对象，不增强
        ((Target2) o2).bar();


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
            System.out.println("target2 bar");
        }
    }

    @Aspect //高级切面类
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
        @Bean // 低级切面
        public Advisor advisor3(MethodInterceptor advice3) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, advice3);
        }

        @Bean
        public MethodInterceptor advice3() {
            return invocation -> {
                System.out.println("advice3 before");
                Object result = invocation.proceed();
                System.out.println("advice3 after");
                return result;
            };
        }
    }
}
