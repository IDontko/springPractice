package com.a12;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gaoyang
 * create on 2022/6/1
 */
public class A12 {
    interface Foo{
        void foo() throws Throwable;

        void bar() throws Throwable;
    }
    static class Target implements Foo{
        @Override
        public void foo(){
            System.out.println("target foo");
        }

        @Override
        public void bar() {
            System.out.println("target bar");
        }
    }
    interface InvocationHandler{
        void invoke(Method method, Object[] args) throws Throwable;
    }

    public static void main(String[] args) {
       Foo proxy  = new $Proxy0(new InvocationHandler() {
           @Override
           public void invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
               System.out.println("before");
               method.invoke(new Target(), args);
               System.out.println("after");

           }
       });

        try {
            proxy.foo();
            proxy.bar();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
