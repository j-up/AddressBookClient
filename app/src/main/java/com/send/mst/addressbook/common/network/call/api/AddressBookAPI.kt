package com.send.mst.addressbookclient.common.network.call.api

import retrofit2.Call
import retrofit2.http.POST

interface AddressBookAPI{
    @POST("/test")
    fun getTest():Call<String>
}