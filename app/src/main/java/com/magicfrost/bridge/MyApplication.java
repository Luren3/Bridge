package com.magicfrost.bridge;

import android.app.Application;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bridge.getInstance().init(this);
        Bridge.getInstance().registerService(ILogin.class, Login.class);
    }
}
