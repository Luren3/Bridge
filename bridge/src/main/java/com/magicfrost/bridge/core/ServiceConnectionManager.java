package com.magicfrost.bridge.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;
import com.magicfrost.bridge.service.BridgeService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class ServiceConnectionManager {

    private final static Object lock = new Object();
    private volatile static ServiceConnectionManager instance;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private BridgeAIDL bridgeAIDL;
    private Context mContext;
    private ConnectHandler handler = new ConnectHandler();
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("Bridge", "Bridge 与远程服务断开连接");
            bind(mContext);
            handler.sendEmptyMessageDelayed(2, 60000);
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("Bridge", "Bridge 连接远程服务成功");
            synchronized (lock) {
                lock.notifyAll();
            }
            handler.removeCallbacksAndMessages(null);
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

    public void bind(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        Intent intent = new Intent(context, BridgeService.class);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public Response request(final Request request) {

        Future<Response> result = threadPool.submit(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                if (bridgeAIDL == null) {
                    bind(mContext);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return bridgeAIDL.send(request);
            }
        });

        Response response = null;

        try {
            response = result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private class ConnectHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
            if (msg.what == 2) {
                if (bridgeAIDL == null) {
                    bind(mContext);
                    sendEmptyMessageDelayed(2, 60000);
                } else {
                    removeCallbacksAndMessages(null);
                }
            }
        }
    }
}
