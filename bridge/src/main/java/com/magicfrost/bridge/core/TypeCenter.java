package com.magicfrost.bridge.core;

import android.text.TextUtils;

import com.magicfrost.bridge.bean.RequestBean;
import com.magicfrost.bridge.bean.RequestParameter;
import com.magicfrost.bridge.utils.TypeUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class TypeCenter {

    private static final TypeCenter ourInstance = new TypeCenter();
    //为了减少反射，所以保存起来
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> mMethods;
    private final ConcurrentHashMap<String, Class<?>> mClazz;

    public TypeCenter() {
        mMethods = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>>();
        mClazz = new ConcurrentHashMap<>();
    }

    public static TypeCenter getInstance() {
        return ourInstance;
    }

    public void register(Class<?> clazz, Class<?> clazzImpl) {
        //注册--》类， 注册--》方法
        registerClass(clazz);
        registerMethod(clazzImpl);
    }

    //缓存class
    private void registerClass(Class<?> clazz) {
        String name = clazz.getName();
        mClazz.putIfAbsent(name, clazz);
    }

    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            mMethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap<String, Method> map = mMethods.get(clazz);
            String methodId = TypeUtils.getMethodId(method);
            map.put(methodId, method);
        }
    }

    public Class<?> getClassType(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = mClazz.get(name);
        if (clazz == null) {
            try {
                clazz = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    public Method getMethod(Class<?> clazz, RequestBean requestBean) {
        String methodName = requestBean.getMethodName();
        if (methodName != null) {
            mMethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap<String, Method> methods = mMethods.get(clazz);
            Method method = methods.get(methodName);
            if (method != null) {
                return method;
            }
            int pos = methodName.indexOf('(');

            Class[] paramters = null;
            RequestParameter[] requestParameters = requestBean.getRequestParameter();
            if (requestParameters != null && requestParameters.length > 0) {
                paramters = new Class[requestParameters.length];
                for (int i = 0; i < requestParameters.length; i++) {
                    paramters[i] = getClassType(requestParameters[i].getParameterClassName());
                }
            }
            method = TypeUtils.getMethod(clazz, methodName.substring(0, pos), paramters);
            methods.put(methodName, method);
            return method;
        }
        return null;
    }
}
