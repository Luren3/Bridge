package com.magicfrost.client

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.magicfrost.bridge.Bridge
import com.magicfrost.ipc.ILogin
import com.magicfrost.ipc.test.ITest
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val login: ILogin? = Bridge.getInstance().getRemoteService<ILogin>(ILogin::class.java)

        val test: ITest? = Bridge.getInstance().getRemoteService<ITest>(ITest::class.java)

        Log.e("Client", "--login--${login}---")

        Log.e("Client", "--test--${test}---")

        loginBt.setOnClickListener {
            login?.login("1889", "3232", object : LoginCallback() {
                override fun onLoginSuccess() {
                    Log.e("LoginActivity", "----onSuccess")
                }

                override fun onError(msg: String) {
                    Log.e("LoginActivity", "----onError($msg)")
                }
            })
            finish()
            test?.test()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LoginActivity", "----onDestroy")
    }
}
