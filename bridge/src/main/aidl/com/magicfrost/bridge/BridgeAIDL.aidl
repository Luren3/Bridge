// Bridge.aidl
package com.magicfrost.bridge;

import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
// Declare any non-default types here with import statements

interface BridgeAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    Response send(in Request request);
}
