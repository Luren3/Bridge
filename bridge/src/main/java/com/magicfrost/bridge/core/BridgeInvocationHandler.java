package com.magicfrost.bridge.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.magicfrost.bridge.Bridge;
import com.magicfrost.bridge.bean.ResponseBean;
import com.magicfrost.bridge.internal.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by MagicFrost on 2019-07-09.
 */
public class BridgeInvocationHandler implements InvocationHandler {

    private Class aClass;

    private Gson gson = new Gson();

    public BridgeInvocationHandler(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Response response = Bridge.getInstance().request(aClass, method, args);

        if (response != null && !TextUtils.isEmpty(response.getData())) {
            ResponseBean responseBean = gson.fromJson(response.getData(), ResponseBean.class);
            if (responseBean.getData() != null) {
                Object responseBeanData = responseBean.getData();
                String data = gson.toJson(responseBeanData);
                Class returnType = method.getReturnType();
                Object result = gson.fromJson(data, returnType);
                return result;
            }
        }
        return null;
    }
}
