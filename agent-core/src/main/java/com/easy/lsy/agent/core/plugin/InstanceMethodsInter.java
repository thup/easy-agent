package com.easy.lsy.agent.core.plugin;

import com.easy.lsy.agent.core.config.Constants;
import com.easy.lsy.agent.core.span.CallInfo;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstanceMethodsInter {

    @RuntimeType
    public static Object intercept(// 被拦截的目标对象 （动态生成的目标对象）
                                   //@This  Object target,
                                   // 正在执行的方法Method 对象（目标对象父类的Method）
                                   @Origin Method method,
                                   // 正在执行的方法的全部参数
                                   @AllArguments Object[] arguments,
                                   // 目标对象的一个代理
                                   @Super Object delegate,
                                   // 方法的调用者对象 对原始方法的调用依靠它
                                   @SuperCall Callable<?> callable) throws Exception {

        CallInfo callInfo = new CallInfo();
        callInfo.setEventType(Constants.SELF_CALL);
        callInfo.setClassName(method.getDeclaringClass().getName());
        callInfo.setMethodName(method.getName());
        callInfo.setParams(arguments);
        //todo 待确认每个参数方法(@test等注解的入口切面)的入口
        //callInfo.setDepth(GlobalData.globalDepth);

        System.out.println("监控 - Begin instance beforeMethod");

        System.out.println("对象类型名称： " + callInfo.getClassName());
        System.out.println("方法名称： " + callInfo.getMethodName());
        System.out.println("入参个数： " + method.getParameterCount());

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < method.getParameterCount(); i++) {
            params.append(arguments[i]).append(",");
            System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + arguments[i]);
        }

        System.out.println("出参类型： " + method.getReturnType().getName());
        System.out.println("监控 - End instance beforeMethod\r\n");

        //FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);
        before(method);

        try {
            // 调用目标方法
            Object result = callable.call();

            CallInfo callInfo2 = new CallInfo();
            callInfo2.setEventType(Constants.SELF_RETURN);
            callInfo2.setClassName(method.getDeclaringClass().getName());
            callInfo2.setMethodName(method.getName());
            callInfo2.setParams(arguments);
            //todo 待确认每个参数方法(@test等注解的入口切面)的入口
            //callInfo2.setDepth(GlobalData.globalDepth);
            callInfo2.setReturnObj(result);

            System.out.println("监控 - Begin instance afterMethod");
            System.out.println("对象类型名称： " + callInfo2.getClassName());
            System.out.println("方法名称： " + callInfo2.getMethodName());
            System.out.println("入参个数： " + method.getParameterCount());
            for (int i = 0; i < method.getParameterCount(); i++) {
                System.out.println("入参 Idx： " + (i + 1) + " 类型： " + method.getParameterTypes()[i].getTypeName() + " 内容： " + arguments[i]);
            }
            System.out.println("出参类型： " + method.getReturnType().getName());
            System.out.println("出参结果： " + result);
            //System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("监控 - End instance afterMethod \r\n");

            //FileUtils.saveYaml(Constants.RESULT_PATH, callInfo);

            return result;
        } finally {
            //目标方法执行后执行日志记录
            System.out.println("方法执行完成Method="+method.getName());
            after();
        }
    }

    public static void after() {

        System.out.println("after== ");
    }

    private static void before(Method method) {

        System.out.println("before== ");
    }

}