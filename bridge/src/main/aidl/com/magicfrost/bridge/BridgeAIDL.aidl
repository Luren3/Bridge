// Bridge.aidl
package com.magicfrost.bridge;

import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
// Declare any non-default types here with import statements

interface BridgeAIDL {
    Response send(in Request request);
}
