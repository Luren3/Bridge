package com.magicfrost.bridge;

import android.os.Bundle;
import android.os.RemoteException;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class Login implements ILogin {

    @Override
    public void login(String mobile, String password, com.magicfrost.bridge.BridgeCallback callback) {
        if (mobile.length() != 0 && password.length() != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("success", "success");
            try {
                callback.onSuccess(bundle);
                Bridge.getInstance().setMessage(bundle);
            } catch (RemoteException e) {
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
