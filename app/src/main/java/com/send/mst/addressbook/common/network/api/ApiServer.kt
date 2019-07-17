package com.send.mst.addressbook.common.network.api

import com.send.mst.addressbook.model.AddressBookListModel
import com.send.mst.addressbook.model.AddressBookModel
import com.send.mst.addressbook.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author JiMinLee
 * @desc
 **/
interface ApiServer {


    /**
     * @author: JiMinLee
     * @param: UserModel
     * @return: Int
     * @description: 회원가입
     **/
    @POST("/signUp")
    fun signUpPost(@Body userVO: UserModel) : Call<Int>

    /**
     * @author: JiMinLee
     * @param: UserModel
     * @return: Int
     * @description: 이메일 중복검사
     **/
    /*
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    ) */
    @POST("/id/check/")
    fun idCheckPost(@Body userVO: UserModel) : Call<Int>

    /**
     * @author: JiMinLee
     * @param: UserModel
     * @return: Int
     * @description: 로그인
     **/
    /*
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    ) */
    @POST("/login")
    fun loginPost(@Body userVO: UserModel) : Call<Int>





    /*
    주소록
     */
    @POST("/addressBook/search")
    fun getAddressBook(@Body addressBookModel: AddressBookModel) : Call<AddressBookListModel>
}
