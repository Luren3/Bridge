// Bridge.aidl
package com.magicfrost.bridge;

import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
import com.magicfrost.bridge.IPCCallback;
import com.magicfrost.bridge.Receiver;

interface BridgeAIDL {
    Response send(in Request request);

    void sendForCallback(in Request request,IPCCallback callback);

    void registerReceiver(in Receiver receiver);

    void unregisterReceiver(in Receiver receiver);
}
