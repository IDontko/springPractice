package com.a12;

import java.lang.reflect.Method;

/**
 * @author gaoyang
 * create on 2022/6/1
 */
public class $Proxy0 implements A12.Foo {
    A12.InvocationHandler h;
    public $Proxy0(A12.InvocationHandler h){
        this.h = h;
    }

    @Override
    public void foo() throws Throwable {
        Method foo = A12.Foo.class.getMethod("foo");
        h.invoke(foo, new Object[0]);
    }

    @Override
    public void bar() throws Throwable {
        Method bar = A12.Foo.class.getMethod("bar");
        h.invoke(bar, new Object[0]);
    }
}
