// Bridge.aidl
package com.magicfrost.bridge;

import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
import com.magicfrost.bridge.BridgeCallback;
import com.magicfrost.bridge.BridgeReceiver;

interface BridgeAIDL {
    Response send(in Request request);

    void sendForCallback(in Request request,BridgeCallback callback);

    void registerReceiver(String packageName,in BridgeReceiver receiver);

    void unregisterReceiver(String packageName);
}
