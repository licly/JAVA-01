package com.charlie.course9;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class LoggerAdvisor {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("打开极客时间");
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("关闭极客时间");
    }
}
