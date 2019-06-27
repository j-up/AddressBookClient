package com.send.mst.addressbook.common.utils

import android.os.Vibrator
import com.send.mst.addressbook.common.network.api.AddressBookAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: JiMinLee
 * @description: 자주사용되는 정보를 모아놓은 클래스
 **/

enum class AppProp(val value: String) {

    SERVER_ADDR("http://192.168.3.25:8080"),
    RESTFUL_ADDRESSBOOK_SEARCH("/addressBook/search");

    // 싱글톤 객체
    object singletonObject {
        val retrofit: Retrofit
        var vibrator:Vibrator? = null
        var addressBookApi: AddressBookAPI? = null

        init {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(AppProp.SERVER_ADDR.value)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}