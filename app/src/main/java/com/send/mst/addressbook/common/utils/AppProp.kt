package com.send.mst.addressbook.common.utils

/**
 * @author: JiMinLee
 * @description: 상수를 모아놓은 클래스
 **/

enum class AppProp(val value: String) {

    SERVER_ADDR("http://192.168.3.25:8080"),

    SHARED_PREFERENCES_FILE_NAME("prefs"),
    SHARED_PREFERENCES_KEY_COOKIE(""),
    STATUS_MESSAGE_SING_UP_SUCCESS("회원가입 성공"),
    STATUS_MESSAGE_INPUT_IS_NULL("공백을 입력해주세요"),
    STATUS_MESSAGE_ALREADY_EXISTS_NO_CHECK("중복 검사를 실행하세요"),
    STATUS_MESSAGE_PASSWORD_NOT_PAIR("패스워드 불일치"),
    STATUS_MESSAGE_SING_UP_FAIL("회원가입 실패"),
    STATUS_MESSAGE_ALREADY_EXISTS_EMAIL("이미 사용중인 계정입니다"),
    STATUS_MESSAGE_IS_NOT_EMAIL("올바른 email 형식을 입력하세요"),
    STATUS_MESSAGE_POSSIBLE_EMAIL("사용 가능한 계정입니다"),
    STATUS_MESSAGE_INTERNAL_SERVER_ERROR("서버 에러"),
    STATUS_MESSAGE_LOGIN_SUCCESS("로그인 성공"),
    STATUS_MESSAGE_LOGIN_FAIL("로그인 실패"),
    STATUS_MESSAGE_CONNECT_FAIL("연결 실패");
}

/**
 * @author: JiMinLee
 * @description: 상수를 모아놓은 클래스
 **/
enum class AppPropInt(val value: Int) {
    CODE_SING_UP_SUCCESS(1),
    CODE_ID_CHECK_SUCCESS(0),
    CODE_LOGIN_FAIL(0),
    CODE_LOGIN_SUCCESS(1);

}