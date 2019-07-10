package com.magicfrost.bridge;

import android.content.Context;

import com.magicfrost.bridge.core.ServiceConnectionManager;
import com.magicfrost.bridge.core.TypeCenter;

/**
 * Created by MagicFrost on 2019-07-08.
 */
public class Bridge {

    private volatile static Bridge instance;
    private Context mContext;
    private TypeCenter typeCenter;
    private ServiceConnectionManager connectionManager;

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
    }

    public void registerService(Class<?> service, Class<?> serviceImpl) {
        typeCenter.register(service, serviceImpl);
    }

    public void connectService() {
        if (mContext == null) {
            throw new IllegalThreadStateException("Bridge not init");
        }

        connectionManager.bind(mContext);
    }

    public <T> T getRemoteService(Class<?> service) {

        return null;
    }
}
