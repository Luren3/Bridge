// IPCCallback.aidl
package com.magicfrost.bridge;
import android.os.Bundle;
// Declare any non-default types here with import statements

interface IPCCallback {
    void onSuccess(in Bundle result);

    void onFail(int code,String msg);
}
