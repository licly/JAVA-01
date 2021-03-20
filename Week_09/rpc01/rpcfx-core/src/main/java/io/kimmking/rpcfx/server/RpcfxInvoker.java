package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public <T> RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        Class<?> sc;
        try {
            sc = Class.forName(request.getServiceClass());
        } catch (ClassNotFoundException e) {
            RpcfxException exception = new RpcfxException(e.getCause());
            response.setException(exception);
            response.setStatus(false);
            return response;
        }

        // 作业1：改成泛型和反射
        //this.applicationContext.getBean(serviceClass);
        Object service = resolver.resolve(sc);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod(), request.getParams());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            RpcfxException exception = new RpcfxException(e.getCause());
            response.setException(exception);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName, Object[] params) throws NoSuchMethodException {
        Class<?>[] paramClasses = new Class<?>[params.length];
        for (int i = 0; i < paramClasses.length; i++) {
            paramClasses[i] = params[i].getClass();
        }
        return klass.getMethod(methodName, paramClasses);
    }

}
