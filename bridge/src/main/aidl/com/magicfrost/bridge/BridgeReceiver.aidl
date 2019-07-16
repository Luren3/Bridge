// Receiver.aidl
package com.magicfrost.bridge;
import android.os.Bundle;
// Declare any non-default types here with import statements

interface BridgeReceiver {

    void onReceive(in Bundle message);
}
