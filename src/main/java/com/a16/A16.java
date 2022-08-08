package com.a16;

import com.a13.Target;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author gaoyang
 * create on 2022/6/8
 * 切点匹配的问题
 */
public class A16 {


    public static void main(String[] args) throws NoSuchMethodException {
        //设置切点
//
//       匹配方法
//        AspectJExpressionPointcut pt1 = new AspectJExpressionPointcut();
//        pt1.setExpression("execution(* bar())");
//        System.out.println(pt1.matches(T1.class.getMethod("foo"), T1.class));
//        System.out.println(pt1.matches(T1.class.getMethod("bar"), T1.class));

//        匹配注解
//        AspectJExpressionPointcut pt2 = new AspectJExpressionPointcut();
//        pt2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
//        System.out.println(pt2.matches(T1.class.getMethod("foo"), T1.class));
//        System.out.println(pt2.matches(T1.class.getMethod("bar"), T1.class));
//
//        System.out.println(pt2.matches(T3.class.getMethod("foo"), T3.class));


        //静态匹配器
        StaticMethodMatcherPointcut pt3 = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 检查方法上是否加了 Transactional 注解
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 查看类上是否加了 Transactional 注解
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                return false;
            }
        };


        System.out.println(pt3.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt3.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println(pt3.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(pt3.matches(T3.class.getMethod("foo"), T3.class));

        //动态匹配器,需要检查参数,动态参数一般用的比较少，每次执行方法的时候都要进行检查。
        //静态匹配可以缓存。
        DynamicMethodMatcherPointcut pt4 = new DynamicMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                if (args.length == 0) {
                    return false;
                }
                return true;
            }
        };

        System.out.println(pt4.matches(T1.class.getMethod("hoo", String.class), T1.class, args));
        System.out.println(pt4.matches(T1.class.getMethod("hoo", String.class), T1.class, "test"));

        MethodInterceptor advice = invocation -> {
            System.out.println("before");
            Object result = invocation.proceed();
            System.out.println("after");
            return result;
        };
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pt4, advice);
        ProxyFactory proxyFactory = new ProxyFactory();
        T1 t1 = new T1();
        proxyFactory.setTarget(t1);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setProxyTargetClass(true);
        T1 t1Proxy = ((T1) proxyFactory.getProxy());

        System.out.println(t1Proxy.getClass());
        t1Proxy.foo("foo");

    }

    static class T1 {
        @Transactional
        public void foo(String test) {
            System.out.println("test" + test);
        }

        public void bar() {
        }

        public void hoo(String a) {
        }
    }

    @Transactional
    static class T2 {
        public void foo() {
        }
    }

    @Transactional
    interface I3 {
        void foo();
    }

    static class T3 implements I3 {
        public void foo() {
        }
    }
}

