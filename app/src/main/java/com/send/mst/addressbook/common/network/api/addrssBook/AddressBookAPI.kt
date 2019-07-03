package com.send.mst.addressbook.common.network.api.addrssBook

import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
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
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    )
    @POST("/addressBook/search")
    fun getAddressBook(@Body addressBookVO: AddressBookVO) : Call<AddressBookListVO>
}