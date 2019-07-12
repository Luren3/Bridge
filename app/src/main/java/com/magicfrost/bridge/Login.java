package com.magicfrost.bridge;

import android.util.Log;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class Login implements ILogin {

    @Override
    public void login(String mobile, String password, LoginCallback callback) {
        if (mobile.length() != 0 && password.length() != 0) {
            callback.onSuccess("success");
        } else {
            callback.onFailed(1, "failed");
        }
    }

    @Override
    public void test(Test test) {
        Log.e("dsds", "" + test.id);
    }
}
