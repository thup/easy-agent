package com.easy.lsy.agent.core.span;

import java.io.Serializable;

public class CallInfo implements Serializable {

    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private Object params;
    /**
     * 返回值
     */
    private Object returnObj;
    /**
     * 异常值
     */
    private Object throwObj;
    /**
     * 行号
     */
    private int lineNum;
    /**
     * 深度
     */
    private int depth;
    /**
     * 事件类型
     */
    private String eventType;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(Object returnObj) {
        this.returnObj = returnObj;
    }

    public Object getThrowObj() {
        return throwObj;
    }

    public void setThrowObj(Object throwObj) {
        this.throwObj = throwObj;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

}
