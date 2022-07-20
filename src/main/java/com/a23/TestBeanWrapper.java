package com.a23;

import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

/**
 * @author gaoyang
 * create on 2022/7/11
 */
public class TestBeanWrapper {

    public static void main(String[] args) {
        MyBean target = new MyBean();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(target);
        beanWrapper.setPropertyValue("a", "10");
        beanWrapper.setPropertyValue("b", "hello");
        beanWrapper.setPropertyValue("c", "1999/10/10");
        System.out.println(target);
    }

    static class MyBean {
        private int a;
        private String b;
        private Date c;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public Date getC() {
            return c;
        }

        public void setC(Date c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    ", c=" + c +
                    '}';
        }
    }
}
