package com.send.mst.addressbook.common.network.api

import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author JiMinLee
 * @desc
 **/
interface ServerAPI {


    /**
     * @author: JiMinLee
     * @param: UserVO
     * @return: Int
     * @description: 회원가입
     **/
    @POST("/signUp")
    fun signUpPost(@Body userVO: UserVO) : Call<Int>

    /**
     * @author: JiMinLee
     * @param: UserVO
     * @return: Int
     * @description: 이메일 중복검사
     **/
    /*
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    ) */
    @POST("/id/check/")
    fun idCheckPost(@Body userVO: UserVO) : Call<Int>

    /**
     * @author: JiMinLee
     * @param: UserVO
     * @return: Int
     * @description: 로그인
     **/
    /*
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    ) */
    @POST("/login")
    fun loginPost(@Body userVO: UserVO) : Call<Int>





    /*
    주소록
     */
    @POST("/addressBook/search")
    fun getAddressBook(@Body addressBookVO: AddressBookVO) : Call<AddressBookListVO>
}
