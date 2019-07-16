package com.send.mst.addressbook.common.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.network.api.ServerAPI
import com.send.mst.addressbook.common.utils.AppPropInt
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Response

/**
 * @author JiMinLee
 * @desc
 **/
class SignUpCustomDialog(val dialog: Dialog) {
    private val tag = this.javaClass.toString()

    private var isExists: Boolean = true
    private var isEmail: Boolean = false

    public fun taskSignUp() {

        val dialogInputEmailEditText = dialog?.findViewById(R.id.editText_input_email) as EditText
        val dialogInputPwEditText = dialog?.findViewById(R.id.editText_input_pw) as EditText
        val dialogInputPw2EditText = dialog?.findViewById(R.id.editText_input_pw2) as EditText
        val dialogInputSignUpButton = dialog?.findViewById(R.id.button_input_signUp) as Button
        val dialogInputCancelButton = dialog?.findViewById(R.id.button_input_cancel) as Button
        val dialogInputExistsButton = dialog?.findViewById(R.id.button_input_email_exists) as Button

        isExists = true
        isEmail = false

        // 중복검사버튼
        dialogInputExistsButton.setOnClickListener {
            Utils.onVibe(dialog.context, 100L)
            val inputEmail: String? = dialogInputEmailEditText.text.toString()
            inputEmail?.let { email -> isEmail = Utils.isValidEmail(email) }

            if (!isEmail) {
                Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_IS_NOT_EMAIL.value)
                return@setOnClickListener
            }

            AppProp.SingletonObject.userVo = UserVO(inputEmail, "")

            val responseTask: (response: Response<Int>) -> Unit = {
                if( it.raw().code()!=200) {
                    Utils.showMessage(
                        dialog.context,
                        tag,
                        AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value,
                        "HTTP Code: ${it.raw().code()}"
                    )
                }

                it.body().let { body ->
                    Log.d(tag, "body: ${body.toString()}")
                    // 중복된 아이디가 없을경우
                    if (body == 0) {
                        isExists = false
                        dialogInputEmailEditText.isEnabled = false
                        dialogInputEmailEditText.background = null

                        Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_POSSIBLE_EMAIL.value)
                        dialogInputExistsButton.visibility = View.INVISIBLE
                    } else {
                        Utils.showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_ALREADY_EXISTS_EMAIL.value)
                    }
                }
            }

            AppProp.SingletonObject.serverApi?.idCheckPost(AppProp.SingletonObject.userVo!!)?.enqueue(
                CallBackImpl(
                    dialog.context,
                    tag,
                    responseTask
                )
            )

        }

        // 회원가입버튼
        dialogInputSignUpButton.setOnClickListener {
            Utils.onVibe(dialog.context, 100L)
            val inputEmail: String = dialogInputEmailEditText.text?.toString() ?: ""
            val inputPw: String = dialogInputPwEditText.text?.toString() ?: ""
            val inputPw2: String = dialogInputPw2EditText.text?.toString() ?: ""

            if (!inputCheck(inputEmail, inputPw, inputPw2, dialog.context)) {
                return@setOnClickListener
            }

            AppProp.SingletonObject.userVo = UserVO(inputEmail, inputPw)

            val responseTask: (response: Response<Int>) -> Unit = {
                it.body() ?: Utils.showMessage(
                    dialog.context,
                    tag,
                    AppProp.STATUS_MESSAGE_SING_UP_FAIL.value,
                    "HTTP Code: ${it.raw().code()}"
                )

                it.body().let { body ->
                    Log.d(tag, "body: ${body.toString()}")
                    if (body == AppPropInt.CODE_SING_UP_SUCCESS.value) {
                        Utils.showMessage(
                            dialog.context,
                            tag,
                            AppProp.STATUS_MESSAGE_SING_UP_SUCCESS.value,
                            "HTTP Code: ${it.raw().code()}"
                        )
                    }
                }
                dialog.dismiss()
            }

            AppProp.SingletonObject.serverApi?.signUpPost(AppProp.SingletonObject.userVo!!)?.enqueue(
                CallBackImpl(
                    dialog.context,
                    tag,
                    responseTask
                )
            )
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
    private fun inputCheck(inputEmail:String,inputPw:String,inputPw2:String,dialogContext: Context):Boolean {
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
}
