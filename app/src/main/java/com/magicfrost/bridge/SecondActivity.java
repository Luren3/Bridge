package com.magicfrost.bridge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by MagicFrost on 2019-07-12.
 */
public class SecondActivity extends AppCompatActivity {

    private LoginListener listener = new LoginListener() {
        @Override
        void loginSuccess() {
            Log.e("loginSuccess", "----");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bridge.getInstance().connectService("com.magicfrost.bridge");

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
                login.login("1889", "3232", new LoginCallback() {
                    @Override
                    void onLoginSuccess() {
                        Log.e("onSuccess", "----");
                    }

                    @Override
                    void onError(String msg) {
                        Log.e("onError", "----" + msg);
                    }
                });
            }
        });
        Bridge.getInstance().registerReceiver(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bridge.getInstance().unregisterReceiver(listener);
    }
}
