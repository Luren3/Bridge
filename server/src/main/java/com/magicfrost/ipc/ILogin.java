package com.magicfrost.ipc;

import com.magicfrost.bridge.BridgeCallback;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public interface ILogin {

    public void login(String mobile, String password, BridgeCallback callback);

}
