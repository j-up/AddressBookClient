package com.send.mst.addressbook.common.network.api

import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author: JiMinLee
 * @description: 주소록 API 인터페이스
 **/

interface AddressBookAPI {
    /**
    * @author: JiMinLee
    * @param: AddressBookVO
    * @return: AddressBookListVO
    * @description: 유저의 주소록 다운
    **/
    @POST("/addressBook/search")
    fun postAddressBook(@Body addressBookVO: AddressBookVO) : Call<AddressBookListVO>
}