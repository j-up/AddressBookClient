package com.send.mst.addressbook.common.utils

import android.os.Vibrator
import com.send.mst.addressbook.common.network.api.addrssBook.AddressBookAPI
import com.send.mst.addressbook.common.network.api.user.UserAPI
import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: JiMinLee
 * @description: 싱글톤과 상수(string)을 모아놓은 클래스
 **/

enum class AppProp(val value: String) {

    SERVER_ADDR("http://192.168.3.25:8080"),
    STATUS_MESSAGE_SING_UP_SUCCESS("회원가입 성공"),
    STATUS_MESSAGE_INPUT_IS_NULL("공백을 입력해주세요"),
    STATUS_MESSAGE_ALREADY_EXISTS_NO_CHECK("중복 검사를 실행하세요"),
    STATUS_MESSAGE_PASSWORD_NOT_PAIR("패스워드 불일치"),
    STATUS_MESSAGE_SING_UP_FAIL("회원가입 실패"),
    STATUS_MESSAGE_ALREADY_EXISTS_EMAIL("이미 사용중인 계정입니다"),
    STATUS_MESSAGE_IS_NOT_EMAIL("올바른 email 형식을 입력하세요"),
    STATUS_MESSAGE_POSSIBLE_EMAIL("사용 가능한 계정입니다"),
    STATUS_MESSAGE_CONNECT_FAIL("연결 실패");

    // 싱글톤 객체
    object SingletonObject {
        val retrofit: Retrofit
        var vibrator:Vibrator? = null
        var addressBookApi: AddressBookAPI? = null
        var userApi: UserAPI? = null
        var userVo: UserVO? = null
        init {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(AppProp.SERVER_ADDR.value)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

/**
* @author: JiMinLee
* @description: 상수(int)를 모아놓은 클래스
**/
enum class AppAropInt(val value: Int) {
    CODE_SING_UP_SUCCESS(1)
}