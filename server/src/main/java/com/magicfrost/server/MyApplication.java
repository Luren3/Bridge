package com.magicfrost.server;

import android.app.Application;

import com.magicfrost.bridge.Bridge;
import com.magicfrost.ipc.ILogin;
import com.magicfrost.ipc.Login;

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
