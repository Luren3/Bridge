package com.magicfrost.client;

import android.os.Bundle;

import com.magicfrost.bridge.core.ReceiverListener;

/**
 * Created by MagicFrost on 2019-07-16.
 */
public abstract class LoginListener implements ReceiverListener {

    @Override
    public void onReceived(Bundle message) {
        if (message.getString("success").equals("success")) {
            loginSuccess();
        }
    }

    abstract void loginSuccess();
}
