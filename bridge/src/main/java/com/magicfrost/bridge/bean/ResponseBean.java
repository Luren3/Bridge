package com.magicfrost.bridge.bean;

public class ResponseBean {

    private Object data;

    public ResponseBean(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
