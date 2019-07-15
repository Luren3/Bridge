// Receiver.aidl
package com.magicfrost.bridge;
import android.os.Bundle;
// Declare any non-default types here with import statements

interface Receiver {

    void onReceive(in Bundle message);
}
