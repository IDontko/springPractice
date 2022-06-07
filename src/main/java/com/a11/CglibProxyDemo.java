package com.a11;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gaoyang
 * create on 2022/6/1
 */
public class CglibProxyDemo {

    static class Target{
        public void  foo(){
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) {
        Target target = new Target();
        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("before");
//            Object result = method.invoke(target, objects); //用方法反射调用目标
            //methodProxy 它可以避免反射调用
//            Object result = methodProxy.invoke(target, objects);
            Object result = methodProxy.invokeSuper(o, args); // 内部没有用反射, 需要代理
            System.out.println("after");

            return result;
        });

        proxy.foo();

    }
}
