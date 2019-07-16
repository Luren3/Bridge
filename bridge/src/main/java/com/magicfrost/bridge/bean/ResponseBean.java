package com.magicfrost.bridge.bean;

/**
 * Created by MagicFrost on 2019-07-12.
 */
public class ResponseBean {

    private Object data;

    public ResponseBean(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
