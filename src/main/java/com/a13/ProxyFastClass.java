package com.a13;

import org.springframework.cglib.core.Signature;

import java.lang.reflect.InvocationTargetException;

public class ProxyFastClass {
    static Signature s0  = new Signature("saveSuper", "()V");
    static Signature s1  = new Signature("saveSuper", "(I)V");
    static Signature s2  = new Signature("saveSuper", "(J)V");
    public int getIndex(Signature signature){
        if (signature.equals(s0)){
            return 0;
        }else if (signature.equals(s1)){
            return 1;
        }else if (signature.equals(s2)){
            return 2;
        }else {
            return -1;
        }
    }

    public Object invoke(int index, Object proxy, Object[] args) throws InvocationTargetException{
        if(index == 0){
            ((Proxy) proxy).saveSuper();
            return null;
        }else if (index == 1){
            ((Proxy) proxy).saveSuper((int)args[0]);
            return null;
        }else if (index == 2){
            ((Proxy) proxy).saveSuper((long)args[0]);
            return null;
        }else {
            throw new RuntimeException("无此方法");
        }

    }

    public static void main(String[] args) throws InvocationTargetException {
        ProxyFastClass proxyFastClass = new ProxyFastClass();
        int index = proxyFastClass.getIndex(new Signature("saveSuper", "()V"));
        System.out.println(index);
        proxyFastClass.invoke(index, new Proxy(), new Object[0]);
    }
}
