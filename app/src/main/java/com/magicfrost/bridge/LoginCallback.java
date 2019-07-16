package com.magicfrost.bridge;

import android.os.Bundle;

import com.magicfrost.bridge.core.BaseCallback;

/**
 * Created by MagicFrost on 2019-07-12.
 */
public abstract class LoginCallback extends BaseCallback {

    @Override
    public void onSucceed(Bundle result) {
        onLoginSuccess();
    }

    @Override
    public void onFailed(int code, String msg) {
        onError(msg);
    }

    abstract void onLoginSuccess();

    abstract void onError(String msg);
}
