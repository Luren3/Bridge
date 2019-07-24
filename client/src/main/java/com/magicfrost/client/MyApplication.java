package com.magicfrost.client;

import android.app.Application;
import android.util.Log;

import com.magicfrost.bridge.Bridge;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bridge.getInstance().init(this);
        Bridge.getInstance().connectService("com.magicfrost.server");

        Bridge.getInstance().registerReceiver(new LoginListener() {
            @Override
            void loginSuccess() {
                Log.e("MyApplication", "----loginSuccess");
            }
        });
    }
}
