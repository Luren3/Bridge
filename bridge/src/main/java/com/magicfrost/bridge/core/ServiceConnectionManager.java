package com.magicfrost.bridge.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.magicfrost.bridge.BridgeAIDL;
import com.magicfrost.bridge.BridgeCallback;
import com.magicfrost.bridge.BridgeReceiver;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MagicFrost on 2019-07-10.
 */
public class ServiceConnectionManager {

    private final static String TAG = "Bridge";
    private static final String BIND_SERVICE_ACTION = "com.magicfrost.bridge.service.BridgeService";
    private final static Object lock = new Object();
    private volatile static ServiceConnectionManager instance;
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private BridgeAIDL bridgeAIDL;
    private Context mContext;
    private String serverPackageName = "";
    private ConnectHandler handler = new ConnectHandler(Looper.getMainLooper());

    private ReceiverListener listener;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "Bridge 与远程服务断开连接");
            bridgeAIDL = null;
            bind(mContext, serverPackageName);
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
            synchronized (lock) {
                lock.notifyAll();
            }
            handler.removeCallbacksAndMessages(null);
            if (listener != null) {
                registerReceiver(listener);
            }
            Log.e(TAG, "Bridge 连接远程服务成功");
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

    public void bind(Context context, String serverPackageName) {
        if (mContext == null) {
            mContext = context;
        }
        this.serverPackageName = serverPackageName;
        Intent intent = new Intent();
        intent.setPackage(serverPackageName);
        intent.setAction(BIND_SERVICE_ACTION);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public Response request(final Request request) {

        Response response = null;

        if (bridgeAIDL == null) {
            bind(mContext, serverPackageName);
        } else {
            try {
                response = bridgeAIDL.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public void request(final Request request, final BridgeCallback callback) {

        final WeakReference<BridgeCallback> callbackWeakReference = new WeakReference<>(callback);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    if (bridgeAIDL == null) {
                        bind(mContext, serverPackageName);
                        try {
                            handler.sendEmptyMessageDelayed(1, 5000);
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (bridgeAIDL != null) {
                        try {
                            if (callbackWeakReference.get() != null) {
                                bridgeAIDL.sendForCallback(request, callbackWeakReference.get());
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        threadPool.execute(runnable);
    }

    public void registerReceiver(final ReceiverListener listener) {

        this.listener = listener;
        if (bridgeAIDL == null) {
            bind(mContext, serverPackageName);
        } else {
            try {
                bridgeAIDL.registerReceiver(mContext.getPackageName(), new BridgeReceiver.Stub() {
                    @Override
                    public void onReceive(final Bundle message) throws RemoteException {
                        if (listener != null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onReceived(message);
                                }
                            });
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterReceiver(ReceiverListener listener) {

        this.listener = null;
        if (bridgeAIDL == null) {
            bind(mContext, serverPackageName);
        } else {
            try {
                bridgeAIDL.unregisterReceiver(mContext.getPackageName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectHandler extends Handler {

        public ConnectHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        }
    }
}
