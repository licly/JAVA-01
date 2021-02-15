package com.charlie.course9;

import java.lang.reflect.Proxy;

/**
 * JDK 动态代理实现AOP
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class JdkProxyAop {

    public static void main(String[] args) {
        Implementation1 obj = new Implementation1();

        Object o = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), (proxy, method, args1) -> {
            System.out.println("极客时间训练营Week04作业");
            method.invoke(obj, args1);
            System.out.println("极客时间训练营Week04作业");
            return proxy;
        });

        Base proxy = (Base) o;
        proxy.printInfo();
    }

     interface Base {
         void printInfo();
     }

     static class Implementation1 implements Base {
         @Override
         public void printInfo() {
             System.out.println("JDK 动态代理实现AOP");
         }
     }
}
