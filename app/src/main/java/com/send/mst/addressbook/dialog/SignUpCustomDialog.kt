package com.send.mst.addressbook.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.utils.AppPropInt
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.utils.SingletonObject
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.model.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * @author JiMinLee
 * @desc
 **/
class SignUpCustomDialog(val dialog: Dialog) {
    private val tag = this.javaClass.toString()

    private var isExists: Boolean = true
    private var isEmail: Boolean = false

    private val dialogInputEmailEditText = dialog?.findViewById(R.id.editText_input_email) as EditText
    private val dialogInputPwEditText = dialog?.findViewById(R.id.editText_input_pw) as EditText
    private val dialogInputPw2EditText = dialog?.findViewById(R.id.editText_input_pw2) as EditText
    private val dialogInputSignUpButton = dialog?.findViewById(R.id.button_input_signUp) as Button
    private val dialogInputCancelButton = dialog?.findViewById(R.id.button_input_cancel) as Button
    private val dialogInputExistsButton = dialog?.findViewById(R.id.button_input_email_exists) as Button

    public fun taskSignUp() {
        isExists = true
        isEmail = false

        // 중복검사버튼
        dialogInputExistsButton.setOnClickListener {
            Utils.onVibe(dialog.context, 100L)
            val inputEmail: String = dialogInputEmailEditText.text.toString()
            inputEmail?.let { inputEmail -> isEmail = Utils.isValidEmail(inputEmail) }

            if (!isEmail) {
                Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_IS_NOT_EMAIL.value)
                return@setOnClickListener
            }

            SingletonObject.userModel = UserModel(inputEmail, "")
            SingletonObject.apiServer.idCheckPost(SingletonObject.userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resultCode ->
                    when(resultCode) {
                        AppPropInt.CODE_ID_CHECK_SUCCESS.value -> doEmailNotExistsSuccessEvent()
                        else -> Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_ALREADY_EXISTS_EMAIL.value)
                    }
                },{
                    Utils.showMessage(dialog.context,tag,AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value,it.message?:AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value)
                })
        }

        // 회원가입버튼
        dialogInputSignUpButton.setOnClickListener {
            Utils.onVibe(dialog.context, 100L)
            val inputEmail: String = dialogInputEmailEditText.text?.toString() ?: ""
            val inputPw: String = dialogInputPwEditText.text?.toString() ?: ""
            val inputPw2: String = dialogInputPw2EditText.text?.toString() ?: ""

            if (!inputCheck(dialog.context,inputEmail, inputPw, inputPw2)) {
                return@setOnClickListener
            }

            SingletonObject.userModel = UserModel(inputEmail, inputPw)

            SingletonObject.apiServer.signUpPost(SingletonObject.userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resultCode ->
                    when(resultCode) {
                        AppPropInt.CODE_SING_UP_SUCCESS.value ->  {
                            Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_SING_UP_SUCCESS.value)
                            dialog.dismiss()
                        }
                        else -> Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_SING_UP_FAIL.value)
                    }
                },{
                    Utils.showMessage(dialog.context,tag,AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value,it.message?:AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value)
                })
        }

        // 취소버튼
        dialogInputCancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    /**
     * @author: JiMinLee
     * @param: inputEmail:String,inputPw:String,inputPw2:String,dialogContext:Context
     * @return: Boolean
     * @description: 입력 값 유효성 처리
     **/
    private fun inputCheck(dialogContext: Context,inputEmail:String,inputPw:String,inputPw2:String):Boolean {
        if(Utils.isNullItems(inputEmail, inputPw, inputPw2) || Utils.isEmptyItems(inputEmail, inputPw, inputPw2)) {
            Utils.showMessage(dialogContext, tag, AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value)
            return false
        }

        if (isExists) {
            Utils.showMessage(dialogContext, tag, AppProp.STATUS_MESSAGE_ALREADY_EXISTS_NO_CHECK.value)
            return false
        }

        if(!inputPw.equals(inputPw2)) {
            Utils.showMessage(dialogContext, tag, AppProp.STATUS_MESSAGE_PASSWORD_NOT_PAIR.value)
            return false
        }
        return true
    }

    private fun doEmailNotExistsSuccessEvent() {
        isExists = false
        dialogInputEmailEditText.isEnabled = false
        dialogInputEmailEditText.background = null

        Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_POSSIBLE_EMAIL.value)
        dialogInputExistsButton.visibility = View.INVISIBLE
    }
}
