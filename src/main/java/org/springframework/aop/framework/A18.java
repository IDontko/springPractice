package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class A18 {
    public A18() {
    }

    public static void main(String[] args) throws Throwable {
        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new A18.Aspect());
        List<Advisor> list = new ArrayList();
        Method[] var3 = A18.Aspect.class.getDeclaredMethods();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            String expression;
            AspectJExpressionPointcut pointcut;
            DefaultPointcutAdvisor advisor;
            if (method.isAnnotationPresent(Before.class)) {
                expression = ((Before)method.getAnnotation(Before.class)).value();
                pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(AfterReturning.class)) {
                expression = ((AfterReturning)method.getAnnotation(AfterReturning.class)).value();
                pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                AspectJAfterReturningAdvice advice = new AspectJAfterReturningAdvice(method, pointcut, factory);
                advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            } else if (method.isAnnotationPresent(Around.class)) {
                expression = ((Around)method.getAnnotation(Around.class)).value();
                pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                AspectJAroundAdvice advice = new AspectJAroundAdvice(method, pointcut, factory);
                advisor = new DefaultPointcutAdvisor(pointcut, advice);
                list.add(advisor);
            }
        }

        Iterator var11 = list.iterator();

        while(var11.hasNext()) {
            Advisor advisor = (Advisor)var11.next();
            System.out.println(advisor);
        }

        A18.Target target = new A18.Target();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(list);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object> methodInterceptorList = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(A18.Target.class.getMethod("foo"), A18.Target.class);
        Iterator var16 = methodInterceptorList.iterator();

        while(var16.hasNext()) {
            Object o = var16.next();
            System.out.println(o);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        MethodInvocation methodInvocation = new ReflectiveMethodInvocation((Object)null, target, A18.Target.class.getMethod("foo"), new Object[0], A18.Target.class, methodInterceptorList);
        methodInvocation.proceed();
    }

    static class Aspect {
        Aspect() {
        }

        @Before("execution(* foo())")
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* foo())")
        public void before2() {
            System.out.println("before2");
        }

        public void after() {
            System.out.println("after");
        }

        @AfterReturning("execution(* foo())")
        public void afterReturning() {
            System.out.println("afterReturning");
        }

        @AfterThrowing("execution(* foo())")
        public void afterThrowing(Exception e) {
            System.out.println("afterThrowing " + e.getMessage());
        }

        @Around("execution(* foo())")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            Object var2;
            try {
                System.out.println("around...before");
                var2 = pjp.proceed();
            } finally {
                System.out.println("around...after");
            }

            return var2;
        }
    }

    static class Target {
        Target() {
        }

        public void foo() {
            System.out.println("target foo");
        }
    }
}