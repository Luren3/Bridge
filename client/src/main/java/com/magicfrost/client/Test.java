package com.magicfrost.client;

import android.os.Bundle;
import android.os.RemoteException;

import com.magicfrost.bridge.BridgeCallback;

/**
 * Created by huangwei on 2019-07-24.
 */
public class Test extends BridgeCallback.Stub {
    @Override
    public void onSuccess(Bundle result) throws RemoteException {

    }

    @Override
    public void onFail(int code, String msg) throws RemoteException {

    }
}
