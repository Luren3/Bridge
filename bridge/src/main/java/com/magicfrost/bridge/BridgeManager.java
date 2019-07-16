package com.magicfrost.bridge;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.magicfrost.bridge.bean.ResponseMake;
import com.magicfrost.bridge.internal.Request;
import com.magicfrost.bridge.internal.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MagicFrost on 2019-07-16.
 */
public class BridgeManager {

    private volatile static BridgeManager instance;

    private HashMap<String, com.magicfrost.bridge.BridgeReceiver> receiverMap = new HashMap<>();

    private BridgeManager() {

    }

    public static BridgeManager getInstance() {
        if (instance == null) {
            synchronized (Bridge.class) {
                if (instance == null) {
                    instance = new BridgeManager();
                }
            }
        }
        return instance;
    }

    public Response send(Request request) {
        ResponseMake responseMake = new ResponseMake();
        return responseMake.makeResponse(request);
    }

    public void sendForCallback(Request request, com.magicfrost.bridge.BridgeCallback callback) {
        ResponseMake responseMake = new ResponseMake();
        responseMake.makeResponse(request, callback);
    }

    public void registerReceiver(String packageName, com.magicfrost.bridge.BridgeReceiver receiver) {
        if (!receiverMap.containsKey(packageName)) {
            try {
                receiver.asBinder().linkToDeath(new ClientDeathRecipient(packageName), 0);
                receiverMap.put(packageName, receiver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterReceiver(String packageName) {
        receiverMap.remove(packageName);
    }

    public void cleanReceiver() {
        receiverMap.clear();
    }

    public void notifyReceiver(Bundle message) {
        Map<String, com.magicfrost.bridge.BridgeReceiver> map = copyMap(receiverMap);
        for (String packageName : map.keySet()) {
            com.magicfrost.bridge.BridgeReceiver receiver = map.get(packageName);
            if (receiver != null) {
                try {
                    receiver.onReceive(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //防止在循环过程中，客户端断开连接，Receiver被remove，造成空指针
    private Map<String, com.magicfrost.bridge.BridgeReceiver> copyMap(Map<String, com.magicfrost.bridge.BridgeReceiver> map) {
        Map<String, com.magicfrost.bridge.BridgeReceiver> copyMap = new HashMap<>();
        for (String packageName : map.keySet()) {
            copyMap.put(packageName, map.get(packageName));
        }
        return copyMap;
    }

    private class ClientDeathRecipient implements IBinder.DeathRecipient {

        private String packageName = "";

        public ClientDeathRecipient(String packageName) {
            this.packageName = packageName;
        }

        @Override
        public void binderDied() {
            receiverMap.remove(packageName);
        }
    }
}
