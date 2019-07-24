package com.magicfrost.bridge.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.magicfrost.bridge.BridgeCallback;
import com.magicfrost.bridge.core.TypeCenter;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MagicFrost on 2019-07-12.
 */
public class ResponseMake {

    private Class<?> resultClass;

    private Object[] mParameters;

    private Gson gson = new Gson();

    private TypeCenter typeCenter = TypeCenter.getInstance();

    private Method mMethod;

    private Object invokeMethod() {
        try {
            return mMethod.invoke(resultClass.newInstance(), mParameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setMethod(RequestBean requestBean) {
        Method method = typeCenter.getMethod(resultClass, requestBean);

        mMethod = method;
    }

    public Response makeResponse(Request request) {
        RequestBean requestBean = gson.fromJson(request.getData(), RequestBean.class);
        resultClass = typeCenter.getClassType(requestBean.getClassName());
        if (resultClass == null) {
            Log.e("Bridge", "Sever not found class service,Please check if it is registered, or if it is under the same package name");
            return null;
        }
        RequestParameter[] requestParameters = requestBean.getRequestParameter();
        if (requestParameters != null && requestParameters.length > 0) {
            mParameters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];

                try {
                    Class<?> clazz = Class.forName(requestParameter.getParameterClassName());
                    mParameters[i] = gson.fromJson(requestParameter.getParameterValue(), clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            mParameters = new Object[0];
        }

        setMethod(requestBean);

        Object resultObject = invokeMethod();

        ResponseBean responseBean = new ResponseBean(resultObject);

        String data = gson.toJson(responseBean);

        Response response = new Response(data);

        return response;
    }

    public void makeResponse(Request request, BridgeCallback callback) {
        RequestBean requestBean = gson.fromJson(request.getData(), RequestBean.class);
        resultClass = typeCenter.getClassType(requestBean.getClassName());
        RequestParameter[] requestParameters = requestBean.getRequestParameter();
        if (requestParameters != null && requestParameters.length > 0) {
            mParameters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];

                try {
                    if (requestParameter.isAnonymousClass()) {
                        mParameters[i] = callback;
                    } else {
                        Class<?> clazz = Class.forName(requestParameter.getParameterClassName());
                        mParameters[i] = gson.fromJson(requestParameter.getParameterValue(), clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            mParameters = new Object[0];
        }

        setMethod(requestBean);

        invokeMethod();
    }
}
