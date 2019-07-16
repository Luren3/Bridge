package com.magicfrost.bridge.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.BridgeCallback;
import com.magicfrost.bridge.BridgeManager;
import com.magicfrost.bridge.BridgeReceiver;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

/**
 * Created by MagicFrost on 2019-07-09.
 */
public class BridgeService extends Service {

    private IBinder mBinder = new BridgeAIDL.Stub() {

        @Override
        public Response send(Request request) throws RemoteException {
            return BridgeManager.getInstance().send(request);
        }

        @Override
        public void sendForCallback(Request request, BridgeCallback callback) throws RemoteException {
            BridgeManager.getInstance().sendForCallback(request, callback);
        }

        @Override
        public void registerReceiver(String packageName, BridgeReceiver receiver) throws RemoteException {
            BridgeManager.getInstance().registerReceiver(packageName, receiver);
        }

        @Override
        public void unregisterReceiver(String packageName) throws RemoteException {
            BridgeManager.getInstance().unregisterReceiver(packageName);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BridgeManager.getInstance().cleanReceiver();
    }
}
