package com.magicfrost.bridge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        Bridge.getInstance().connectService()

        val login2 = Bridge.getInstance().getRemoteService<ILogin>(ILogin::class.java)

        login.setOnClickListener {

        }
    }
}
