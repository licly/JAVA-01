package com.charlie.course9;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * ByteBuddy实现AOP
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class ByteBuddyAop {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<GeekTimeLearning> unload = new ByteBuddy()
                .subclass(GeekTimeLearning.class)
                .method(ElementMatchers.any())
                .intercept(Advice.to(LoggerAdvisor.class))
                .make();
        Class<?> clazz = unload.load(unload.getClass().getClassLoader()).getLoaded();
        GeekTimeLearning o = (GeekTimeLearning) clazz.newInstance();
        o.learnJava();
    }

}
