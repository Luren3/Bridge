package com.magicfrost.bridge.bean;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class RequestParameter {

    private String parameterClassName;
    private String parameterValue;
    private boolean isAnonymousClass;

    public RequestParameter() {

    }

    public RequestParameter(String parameterClassName, String parameterValue) {
        this.parameterClassName = parameterClassName;
        this.parameterValue = parameterValue;
    }

    public String getParameterClassName() {
        return parameterClassName;
    }

    public void setParameterClassName(String parameterClassName) {
        this.parameterClassName = parameterClassName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public boolean isAnonymousClass() {
        return isAnonymousClass;
    }

    public void setAnonymousClass(boolean anonymousClass) {
        isAnonymousClass = anonymousClass;
    }
}
