package com.send.mst.addressbook.common.network.api

import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author JiMinLee
 * @desc 주소록 API 인터페이스
 **/

interface AddressBookAPI {

    @POST("/addressBook/search")
    fun postAddressBook(@Body addressBookVO: AddressBookVO) : Call<AddressBookListVO>
}