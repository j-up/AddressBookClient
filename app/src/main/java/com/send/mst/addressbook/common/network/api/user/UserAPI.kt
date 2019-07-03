package com.send.mst.addressbook.common.network.api.user

import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author JiMinLee
 * @desc
 **/
interface UserAPI {

    /**
     * @author: JiMinLee
     * @param: UserVO
     * @return: Int
     * @description: 회원가입
     **/
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    )
    @POST("/signUp")
    fun signUpPost(@Body userVO: UserVO) : Call<Int>

    /**
     * @author: JiMinLee
     * @param: UserVO
     * @return: Int
     * @description: 이메일 중복검사
     **/
    @Headers(
        "Authorization: Basic amltaW46amltaW4x",
        "Content-Type: application/json"
    )
    @POST("/id/check/")
    fun idCheckPost(@Body userVO: UserVO) : Call<Int>
}
