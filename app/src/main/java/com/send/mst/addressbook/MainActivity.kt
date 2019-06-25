package com.send.mst.addressbookclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.send.mst.addressbookclient.common.AppConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appConfig = AppConfig()

        appConfig.initRetrofitInit()
    }
}
