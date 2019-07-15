package com.magicfrost.bridge.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.IPCCallback;
import com.magicfrost.bridge.Receiver;
import com.magicfrost.bridge.bean.ResponseMake;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

/**
 * Created by MagicFrost on 2019-07-09.
 */
public class BridgeService extends Service {

    private IBinder mBinder = new BridgeAIDL.Stub() {

        @Override
        public Response send(Request request) throws RemoteException {
            ResponseMake responseMake = new ResponseMake();
            return responseMake.makeResponse(request);
        }

        @Override
        public void sendForCallback(Request request, IPCCallback callback) throws RemoteException {
            Log.e("dsdsd", "2-" + callback);
            ResponseMake responseMake = new ResponseMake();
            responseMake.makeResponse(request, callback);
        }

        @Override
        public void registerReceiver(Receiver receiver) throws RemoteException {

        }

        @Override
        public void unregisterReceiver(Receiver receiver) throws RemoteException {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
