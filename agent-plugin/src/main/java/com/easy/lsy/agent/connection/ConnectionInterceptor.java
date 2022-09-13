package com.easy.lsy.agent.connection;

import com.alibaba.fastjson.JSONObject;
import com.easy.lsy.agent.core.config.Constants;
import com.easy.lsy.agent.core.config.GlobalData;
import com.easy.lsy.agent.core.logging.api.ILog;
import com.easy.lsy.agent.core.logging.api.LogManager;
import com.easy.lsy.agent.core.plugin.intercept.enhance.InstanceMethodsAroundInterceptor;
import com.easy.lsy.agent.core.plugin.intercept.enhance.MethodInterceptResult;
import com.easy.lsy.agent.core.span.CallInfo;
import com.easy.lsy.agent.core.utils.FileUtils;

import java.lang.reflect.Method;

public class ConnectionInterceptor implements InstanceMethodsAroundInterceptor {
    private static final ILog logger = LogManager.getLogger(ConnectionInterceptor.class);
    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
        MethodInterceptResult result) throws Throwable {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.SELF_CALL);
        callInfo.setClassName(objInst.getClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(allArguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        callInfo.setDepth(GlobalData.globalDepth);

        System.out.println("监控 - Begin instance beforeMethod");

        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("入参个数： " + method.getParameterCount());

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < method.getParameterCount(); i++) {
            params.append(allArguments[i]).append(",");
            System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + allArguments[i]);
        }

        System.out.println("出参类型： " + method.getReturnType().getName());
        System.out.println("监控 - End instance beforeMethod\r\n");

        FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
        Object ret) throws Throwable {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.SELF_RETURN);
        callInfo.setClassName(objInst.getClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(allArguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        callInfo.setDepth(GlobalData.globalDepth);
        callInfo.setReturnObj(ret);

        System.out.println("监控 - Begin instance afterMethod");
        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("入参个数： " + method.getParameterCount());
        for (int i = 0; i < method.getParameterCount(); i++) {
            System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + allArguments[i]);
        }
        System.out.println("出参类型： " + method.getReturnType().getName());
        System.out.println("出参结果： " + ret);
        //System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("监控 - End instance afterMethod \r\n");

        FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
        Throwable t) {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.SELF_THROWING);
        callInfo.setClassName(objInst.getClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(allArguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        callInfo.setDepth(GlobalData.globalDepth);
        callInfo.setThrowObj(t);

        System.out.println("监控 - Begin instance handleMethodException");
        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("Throwable类型： " + t);
        System.out.println("监控 - End instance handleMethodException\r\n");

        FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
    }
}
