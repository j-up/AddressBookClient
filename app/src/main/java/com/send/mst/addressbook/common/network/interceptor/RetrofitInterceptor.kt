package com.send.mst.addressbook.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * @author JiMinLee
 * @desc 서버와 통신전에 연결을 가로채는 인터셉터
 **/
class RetrofitInterceptor:Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val builder: Request.Builder = chain.request().newBuilder()
            builder.addHeader("Authorization","Basic amltaW46amltaW4x")
            builder.addHeader("Content-Type","application/json")

        return chain.proceed(builder.build())
    }
}