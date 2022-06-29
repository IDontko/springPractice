package com.a13;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class A13 {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\Work_CodePractice\\springPractice\\src\\main\\java\\com\\a13");
        Proxy proxy = new Proxy();
        Target target = new Target();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object p, Method method, Object[] args,
                                    MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
//                return method.invoke(target, args); // 反射调用
                // FastClass
//                return methodProxy.invoke(target, args); // 内部无反射, 结合目标用
                return methodProxy.invokeSuper(p, args); // 内部无反射, 结合代理用
                //为啥不能这么写? 因为如果调用代理类的 实现接口，该接口会继续进入intercept方法，导致死循环。
//                return methodProxy.invoke(p, args);
            }
        });

        proxy.save();
        proxy.save(1);
        proxy.save(2L);
    }
}
