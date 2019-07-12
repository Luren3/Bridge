package com.magicfrost.bridge.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
import com.magicfrost.bridge.service.BridgeService;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class ServiceConnectionManager {

    private volatile static ServiceConnectionManager instance;

    private BridgeAIDL bridgeAIDL;

    private Context mContext;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("Bridge", "Bridge 与远程服务断开连接");
            bind(mContext);
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                bridgeAIDL = BridgeAIDL.Stub.asInterface(service);
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("Bridge", "Bridge 连接远程服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnectionManager() {

    }

    public static ServiceConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ServiceConnectionManager.class) {
                if (instance == null) {
                    instance = new ServiceConnectionManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void bind(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        Intent intent = new Intent(context, BridgeService.class);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public Response request(final Request request) {

        Response response = null;

        if (bridgeAIDL == null) {
            bind(mContext);
        } else {
            try {
                response = bridgeAIDL.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
