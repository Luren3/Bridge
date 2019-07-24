package com.magicfrost.server;

import android.app.Application;

import com.magicfrost.bridge.Bridge;
import com.magicfrost.ipc.ILogin;
import com.magicfrost.ipc.ITest;
import com.magicfrost.ipc.Login;
import com.magicfrost.ipc.Test;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bridge.getInstance().init(this);
        Bridge.getInstance().registerService(ILogin.class, Login.class);
        Bridge.getInstance().registerService(ITest.class, Test.class);
    }
}
