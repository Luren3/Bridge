package com.magicfrost.bridge.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

/**
 * Created by MagicFrost on 2019-07-09.
 */
public class BridgeService extends Service {

    private IBinder mBinder = new BridgeAIDL.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public Response send(Request request) throws RemoteException {

            return null;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
