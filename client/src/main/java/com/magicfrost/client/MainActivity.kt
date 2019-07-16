package com.magicfrost.client

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.magicfrost.bridge.Bridge
import com.magicfrost.ipc.ILogin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var login: ILogin? = null

        Handler().postDelayed({
            login = Bridge.getInstance().getRemoteService<ILogin>(ILogin::class.java)

            Log.e("Client", "---${login}---")
        }, 2000)

        loginBt.setOnClickListener {
            login?.login("1889", "3232", object : LoginCallback() {
                override fun onLoginSuccess() {
                    Log.e("onSuccess", "----")
                }

                override fun onError(msg: String) {
                    Log.e("onError", "----$msg")
                }
            })
        }
    }
}
