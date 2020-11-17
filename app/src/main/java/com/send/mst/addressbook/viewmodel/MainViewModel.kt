package com.send.mst.addressbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.send.mst.addressbook.common.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers

import com.send.mst.addressbook.model.UserModel
import io.reactivex.schedulers.Schedulers

/**
 * @author JiMinLee
 * @desc
 **/
// ToDo EditText 엔터키 클릭 이벤트
class MainViewModel() : ViewModel() {
    private val _onLoginClick = SingleLiveEvent<Int>()
    private val _onSignUpClick = SingleLiveEvent<Boolean>()
    private val _error = SingleLiveEvent<String>()

    var editTextId = MutableLiveData<String>()
    var editTextPw = MutableLiveData<String>()

    val onSignUpClick: LiveData<Boolean> get() = _onSignUpClick
    val onLoginClick: LiveData<Int> get() = _onLoginClick
    val error: LiveData<String> get() = _error

    fun onSignUpClick() {
        _onSignUpClick.call()
    }
    //ToDo 쿠키처리 -> sns 로그인 처리
    fun onLoginClick() {
        if (Utils.isEmptyItems(editTextId.value,editTextPw.value) || Utils.isNullItems(editTextId.value,editTextPw.value)) {
            _error.value = AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value
            return
        }
        doLogin()
    }

    private fun doLogin() {
        SingletonObject.userModel = UserModel(editTextId.value,editTextPw.value)
        SingletonObject.apiServer.run {
            loginPost(SingletonObject.userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SingletonObject.idIndex = it
                    _onLoginClick.value = it
                }, {
                    _error.value = AppProp.STATUS_MESSAGE_LOGIN_FAIL.value
                })
        }
    }

}