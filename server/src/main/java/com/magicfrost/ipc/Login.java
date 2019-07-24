package com.magicfrost.ipc;

import android.os.Bundle;
import android.os.RemoteException;

import com.magicfrost.bridge.Bridge;
import com.magicfrost.bridge.BridgeCallback;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class Login implements ILogin {

    @Override
    public void login(String mobile, String password, BridgeCallback callback) {
        if (mobile.length() != 0 && password.length() != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("success", "success");
            try {
                Thread.sleep(2000);
                callback.onSuccess(bundle);
                Bridge.getInstance().setMessage(bundle);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                callback.onFail(1, "failed");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
