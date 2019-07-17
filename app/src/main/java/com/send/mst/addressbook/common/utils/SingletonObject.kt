package com.send.mst.addressbook.common.utils

import android.os.Vibrator
import com.send.mst.addressbook.common.network.api.ApiServer
import com.send.mst.addressbook.common.network.interceptor.RetrofitInterceptor
import com.send.mst.addressbook.model.AddressBookModel
import com.send.mst.addressbook.model.UserModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author JiMinLee
 * @desc
 **/
object SingletonObject {
    private val retrofit: Retrofit

    var vibrator: Vibrator? = null
    var idIndex: Int? = null

    val apiServer: ApiServer

    lateinit var userModel: UserModel
    lateinit var addressBookModel: AddressBookModel



    init {
        retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(AppProp.SERVER_ADDR.value)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(RetrofitInterceptor())
                    .build())
            .build()

        apiServer = retrofit.create(ApiServer::class.java)
    }
}