package com.send.mst.addressbookclient.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppConfig {
    lateinit var mRetrofit: Retrofit

    fun initRetrofitInit() {
        mRetrofit = Retrofit.Builder()
            .baseUrl("")
            .build()
    }
}