package com.send.mst.addressbookclient.common.network.call

import com.send.mst.addressbookclient.common.network.call.api.AddressBookAPI
import retrofit2.Retrofit
import retrofit2.create

class AddressBookAPI_Call {

    fun callAdressBook(mRetrofitAPI: Retrofit) {
        mRetrofitAPI.apply {
            create(AddressBookAPI::class.java)
        }
        // mRetrofitAPI.create(AddressBookAPI.class)

    }
}