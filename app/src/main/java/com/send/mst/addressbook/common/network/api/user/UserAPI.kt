package com.send.mst.addressbook.common.network.api.user

import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Call
import retrofit2.http.Body
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
    @POST("/signup")
    fun signupPost(@Body userVO: UserVO) : Call<Int>
}