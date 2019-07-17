package com.send.mst.addressbook.viewmodel

import android.content.Intent
import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.utils.AppPropInt
import com.send.mst.addressbook.common.utils.SingleLiveEvent
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.model.UserModel
import com.send.mst.addressbook.view.AddressBookUpDownActivity
import retrofit2.Response

/**
 * @author JiMinLee
 * @desc
 **/
// ToDo EditText 엔터키 클릭 이벤트
class MainViewModel : ViewModel() {
    private val _onLoginClick = SingleLiveEvent<String>()
    private val _onSignUpClick = SingleLiveEvent<Boolean>()
    private val _isLoginState = SingleLiveEvent<Boolean>()

    var editTextId = MutableLiveData<String>()
    var editTextPw = MutableLiveData<String>()

    val onSignUpClick: LiveData<Boolean> get() = _onSignUpClick
    val onLoginClick: LiveData<String> get() = _onLoginClick
    val isLoginState: LiveData<Boolean> get() = _isLoginState

    fun onSignUpClick() {
        _onSignUpClick.value = true
    }

    fun onLoginClick() {

        if (Utils.isEmptyItems(editTextId.value,editTextPw.value) || Utils.isNullItems(editTextId.value,editTextPw.value)) {
            _onLoginClick.value = AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value
            return
        }
        doLogin()
    }

    fun doLogic(s: Editable) {
        Log.d("MainViewModel",s.toString())
    }

    private fun doLogin() {
        // Login Success
       _isLoginState.value = true

        // Login Fail
        // _isLoginState.value = false
    }


}