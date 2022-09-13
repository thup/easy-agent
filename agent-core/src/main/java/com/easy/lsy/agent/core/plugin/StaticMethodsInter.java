package com.easy.lsy.agent.core.plugin;

import com.alibaba.fastjson.JSONObject;
import com.easy.lsy.agent.core.config.Constants;
import com.easy.lsy.agent.core.config.GlobalData;
import com.easy.lsy.agent.core.span.CallInfo;
import com.easy.lsy.agent.core.utils.FileUtils;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author tlibn
 * @Date 2022/8/29 12:21
 **/
public class StaticMethodsInter {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.GLOBAL_CALL);
        callInfo.setClassName(method.getDeclaringClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(arguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        callInfo.setDepth(GlobalData.globalDepth);

        System.out.println("监控 - Begin Static beforeMethod");

        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("入参个数： " + method.getParameterCount());
        for (int i = 0; i < method.getParameterCount(); i++) {
            System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + arguments[i]);
        }
        System.out.println("出参类型： " + method.getReturnType().getName());
        System.out.println("监控 - End Static beforeMethod\r\n");

        FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return Object ret) {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.GLOBAL_RETURN);
        callInfo.setClassName(method.getDeclaringClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(arguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        callInfo.setDepth(GlobalData.globalDepth);
        callInfo.setReturnObj(ret);

        System.out.println("监控 - Begin Static afterMethod");
        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("入参个数： " + method.getParameterCount());
        for (int i = 0; i < method.getParameterCount(); i++) {
            System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + arguments[i]);
        }
        System.out.println("出参类型： " + method.getReturnType().getName());
        System.out.println("出参结果： " + ret);
        System.out.println("监控 - End Static afterMethod \r\n");

        FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
    }

}