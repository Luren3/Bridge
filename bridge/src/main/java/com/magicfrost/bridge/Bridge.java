package com.magicfrost.bridge;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.magicfrost.bridge.bean.RequestBean;
import com.magicfrost.bridge.bean.RequestParameter;
import com.magicfrost.bridge.core.BaseCallback;
import com.magicfrost.bridge.core.BridgeInvocationHandler;
import com.magicfrost.bridge.core.ReceiverListener;
import com.magicfrost.bridge.core.ServiceConnectionManager;
import com.magicfrost.bridge.core.TypeCenter;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
import com.magicfrost.bridge.utils.TypeUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by MagicFrost on 2019-07-08.
 */
public class Bridge {

    private volatile static Bridge instance;
    private Context mContext;
    private TypeCenter typeCenter;
    private ServiceConnectionManager connectionManager;

    private Gson gson = new Gson();

    private Bridge() {

    }

    public static Bridge getInstance() {
        if (instance == null) {
            synchronized (Bridge.class) {
                if (instance == null) {
                    instance = new Bridge();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        typeCenter = TypeCenter.getInstance();
        connectionManager = ServiceConnectionManager.getInstance();
        connectionManager.init(context);


    }

    public void registerService(Class<?> service, Class<?> serviceImpl) {
        typeCenter.register(service, serviceImpl);
    }

    public void connectService(String serverPackageName) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }

        connectionManager.bind(mContext, serverPackageName);
    }

    public <T> T getRemoteService(Class<?> service) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }
        T proxy = (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new BridgeInvocationHandler(service));
        return proxy;
    }

    public Response request(Class<?> clazz, Method method, Object[] args) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }

        RequestBean requestBean = new RequestBean();

        //set全类名
        requestBean.setClassName(clazz.getName());

        //set方法
        if (method != null) {
            requestBean.setMethodName(TypeUtils.getMethodId(method));
        }

        //set参数
        RequestParameter[] requestParameters = null;
        BaseCallback.Stub callback = null;
        if (args != null && args.length > 0) {
            requestParameters = new RequestParameter[args.length];

            for (int i = 0; i < args.length; i++) {
                Object parameter = args[i];
                String parameterClassName = parameter.getClass().getName();
                Class aClass = parameter.getClass();
                if (aClass.isAnonymousClass()) {
                    callback = (BaseCallback.Stub) parameter;
                    String parameterValue = gson.toJson(parameter);
                    RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                    requestParameter.setAnonymousClass(true);
                    requestParameters[i] = requestParameter;
                } else {
                    String parameterValue = gson.toJson(parameter);
                    RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                    requestParameters[i] = requestParameter;
                }
            }
        }

        if (requestParameters != null) {
            requestBean.setRequestParameter(requestParameters);
        }

        Request request = new Request(gson.toJson(requestBean));

        if (callback != null) {
            connectionManager.request(request, callback);
        } else {
            return connectionManager.request(request);
        }
        return null;
    }

    public void registerReceiver(ReceiverListener listener) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }
        connectionManager.registerReceiver(listener);
    }

    public void unregisterReceiver(ReceiverListener listener) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }
        connectionManager.unregisterReceiver(listener);
    }

    public void setMessage(Bundle message) {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }
        BridgeManager.getInstance().notifyReceiver(message);
    }
}
