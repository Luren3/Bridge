package com.magicfrost.bridge.bean;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class RequestBean {

    //类名
    private String className;

    //返回方法名字
    private String methodName;

    //参数
    private RequestParameter[] requestParameter;

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

    public RequestParameter[] getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(RequestParameter[] requestParameter) {
        this.requestParameter = requestParameter;
    }
}
