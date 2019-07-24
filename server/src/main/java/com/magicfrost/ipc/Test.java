package com.magicfrost.ipc;

import android.util.Log;

/**
 * Created by huangwei on 2019-07-22.
 */
public class Test implements ITest {

    @Override
    public void test() {
        Log.e("Test", "test()");
    }
}
