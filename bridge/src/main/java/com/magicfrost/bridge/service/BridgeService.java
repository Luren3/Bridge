package com.magicfrost.bridge.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.magicfrost.bridge.BridgeAIDL;
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
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
