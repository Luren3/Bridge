package com.magicfrost.bridge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.magicfrost.bridge.core.BaseCallback;

/**
 * Created by huangwei on 2019-07-12.
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bridge.getInstance().connectService();

        Button loginBt = findViewById(R.id.login);

        final ILogin login = Bridge.getInstance().getRemoteService(ILogin.class);

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                login.login("123", "3232", new com.magicfrost.bridge.IPCCallback.Stub() {
//                    @Override
//                    public void onSuccess(Bundle result) throws RemoteException {
//                        Log.e("onSuccess","----");
//                    }
//
//                    @Override
//                    public void onFail(int code, String msg) throws RemoteException {
//                        Log.e("onFail","----");
//                    }
//                });
                login.login("", "3232", new BaseCallback() {
                    @Override
                    public void onSucceed(Bundle result) {
                        Log.e("onSuccess", "----");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.e("onFail", "----" + code + "," + msg);
                    }
                });
            }
        });
    }
}
