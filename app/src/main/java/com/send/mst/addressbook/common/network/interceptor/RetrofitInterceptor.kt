package com.send.mst.addressbook.common.network.interceptor

import android.util.Log
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

            val response:Response = chain.proceed(builder.build())

           /* if(!response.headers("Set-Cookie").isNullOrEmpty()) {

                val cookies = HashSet<String>()

                response.header("Set-Cookie")?.let { cookies ->
                    for(header  in cookies) {
                        Log.d("RetrofitInterceptor",header.toString())
                    }
                }
            }*/
            /*if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                HashSet<String> cookies = new HashSet<>();



                for (String header : originalResponse.headers("Set-Cookie")) {

                    cookies.add(header);

                }



                mDsp.putHashSet(DalgonaSharedPreferences.KEY_COOKIE, cookies);

            }
            [출처] REST API라이브러리 - Retrofit2.0 강좌 - 쿠키 다루기|작성자 달망이*/
        return response
    }
}