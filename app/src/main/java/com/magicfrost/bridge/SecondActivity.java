package com.magicfrost.bridge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
                LoginCallback ds = new LoginCallback() {
                    @Override
                    public void onSuccess(String str) {

                    }

                    @Override
                    public void onFailed(int code, String msd) {

                    }
                };
                Log.e("dsds", "" + ds);
                login.login("18667667150", "123", ds);
//                login.test(new Test("123"));
            }
        });
    }
}
