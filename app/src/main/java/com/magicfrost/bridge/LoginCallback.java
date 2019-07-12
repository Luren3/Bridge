package com.magicfrost.bridge;

/**
 * Created by huangwei on 2019-07-12.
 */
public interface LoginCallback {

    void onSuccess(String str);

    void onFailed(int code, String msd);
}
