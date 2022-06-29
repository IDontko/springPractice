package com.a11;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author gaoyang
 * create on 2022/6/1
 *
 * jdk动态代理只能代理接口
 */
public class JdkProxyDemo {
    interface Foo{
        int foo();
    }

    static class Target implements Foo{

        @Override
        public int foo() {
            System.out.println("target foo");
            return 1;
        }
    }

    public static void main(String[] args) {
        Target target = new Target();
        ClassLoader classLoader = JdkProxyDemo.class.getClassLoader();
        System.out.println(target.getClass().getClassLoader() == classLoader);
        Foo proxy = (Foo)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (p, method, args1) -> {
            System.out.println("before");
            Object result = method.invoke(target, args1);
            System.out.println("after");
            System.out.println(result);
            return result;
        });
        proxy.foo();
    }
}
