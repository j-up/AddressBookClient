package com.send.mst.addressbook.common.di

import com.send.mst.addressbook.common.network.api.ApiServer
import com.send.mst.addressbook.common.network.interceptor.RetrofitInterceptor
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.model.ApiServerImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author JiMinLee
 * @desc
 **/

val appModule = module {
    single { RetrofitInterceptor() }

    single {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(get())
                    .build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppProp.SERVER_ADDR.value)
            .build()
            .create(ApiServer::class.java)
    }

    single { ApiServerImpl() }


}
