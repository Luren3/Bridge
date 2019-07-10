package com.magicfrost.bridge;

import com.magicfrost.bridge.annotion.ClassId;

/**
 * Created by MagicFrost on 2019-07-10.
 */
@ClassId("com.magicfrost.bridge.ILogin")
public interface ILogin {

    public void login(String mobile, String password);
}
