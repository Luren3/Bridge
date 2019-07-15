package com.magicfrost.bridge.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.magicfrost.bridge.IPCCallback;


public abstract class BaseCallback extends IPCCallback.Stub {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public final void onSuccess(final Bundle result) throws RemoteException {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSucceed(result);
            }
        });
    }

    @Override
    public final void onFail(final int code, final String msg) throws RemoteException {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(code, msg);
            }
        });
    }

    public abstract void onSucceed(Bundle result);

    public abstract void onFailed(int code, String msg);
}
