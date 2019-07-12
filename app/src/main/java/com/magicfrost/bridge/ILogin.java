package com.magicfrost.bridge;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public interface ILogin {

    public void login(String mobile, String password, LoginCallback callback);

    public void test(Test test);
}
