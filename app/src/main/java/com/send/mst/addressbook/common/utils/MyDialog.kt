package com.send.mst.addressbook.common.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.send.mst.addressbook.R
import android.view.WindowManager
import com.send.mst.addressbook.common.utils.Utils.Companion.isValidEmail
import com.send.mst.addressbook.common.utils.Utils.Companion.showMessage
import com.send.mst.addressbook.domain.vo.user.UserVO
import android.view.View
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.network.api.user.UserAPI
import com.send.mst.addressbook.common.utils.Utils.Companion.isEmptyItems
import com.send.mst.addressbook.common.utils.Utils.Companion.isNullItems
import retrofit2.Response


/**
 * @author JiMinLee
 * @desc
 **/

class MyDialog {
    private val tag = this.javaClass.toString()
    private var dialog:Dialog? = null

    private var isExists:Boolean= true
    private var isEmail:Boolean = false

        /**
         * @author: JiMinLee
         * @param: context, customDialog id:Int
         * @return:
         * @description:
         **/
        fun createCustomDialog(context: Context,resId: Int) {
            dialog = Dialog(context)

            dialog?.also {
                it.requestWindowFeature(Window.FEATURE_NO_TITLE)
                it.setContentView(resId)

                val lp = WindowManager.LayoutParams()
                lp.copyFrom(it.window.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                val window = it.window
                window.attributes = lp

                it.show()
            }

            when(resId) {
                R.layout.custom_dialog_signup -> {
                    dialog?.let {
                        taskSignUpDialog(it)
                    }
                }
            }
        }

    /**
    * @author: JiMinLee
    * @param: dialog
    * @return:
    * @description: 회원가입 다이얼로그 생성
    **/
    private fun taskSignUpDialog(dialog: Dialog) {
        val dialogInputEmailEditText= dialog?.findViewById(R.id.editText_input_email) as EditText
        val dialogInputPwEditText  =  dialog?.findViewById(R.id.editText_input_pw) as EditText
        val dialogInputPw2EditText =  dialog?.findViewById(R.id.editText_input_pw2) as EditText
        val dialogInputSignUpButton = dialog?.findViewById(R.id.button_input_signUp) as Button
        val dialogInputCancelButton = dialog?.findViewById(R.id.button_input_cancel) as Button
        val dialogInputExistsButton = dialog?.findViewById(R.id.button_input_email_exists) as Button

        isExists= true
        isEmail = false

        // 중복검사
        dialogInputExistsButton.setOnClickListener {
            Utils.onVibe(dialog.context,100L)
            val inputEmail:String? = dialogInputEmailEditText.text.toString()
            inputEmail?.let { email -> isEmail=isValidEmail(email) }

            if (!isEmail) {
                showMessage(dialog.context,tag,AppProp.STATUS_MESSAGE_IS_NOT_EMAIL.value)
                return@setOnClickListener
            }

            if(false) {
                // Todo 중복검사
                showMessage(dialog.context,tag,AppProp.STATUS_MESSAGE_ALREADY_EXISTS_EMAIL.value)
            } else {
                isExists = false
                dialogInputEmailEditText.isEnabled=false
                dialogInputEmailEditText.background=null

                showMessage(dialog.context,tag,AppProp.STATUS_MESSAGE_POSSIBLE_EMAIL.value)
                dialogInputExistsButton.visibility = View.INVISIBLE
            }
        }
        // 회원가입버튼
        dialogInputSignUpButton.setOnClickListener {
            Utils.onVibe(dialog.context,100L)
            val inputEmail:String = dialogInputEmailEditText.text?.toString()?:""
            val inputPw:String= dialogInputPwEditText.text?.toString()?:""
            val inputPw2:String = dialogInputPw2EditText.text?.toString()?:""

            if(!inputCheck(inputEmail,inputPw,inputPw2,dialog.context)) {
                return@setOnClickListener
            }

            Log.d(tag,dialogInputEmailEditText.text.toString())
            Log.d(tag,dialogInputPwEditText.text.toString())
            Log.d(tag,dialogInputPw2EditText.text.toString())

            AppProp.SingletonObject.userVo = UserVO(inputEmail,inputPw)

            AppProp.SingletonObject.userApi = AppProp.SingletonObject.retrofit.let {
                it.create(UserAPI::class.java)
            }
            val responseTask:(response: Response<Int>) -> Unit = {
                it.body()?:showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_SING_UP_FAIL.value, "HTTP Code: ${it.raw().code()}")

                it.body().let{ body ->
                    Log.d(tag,"body: ${body.toString()}")
                    if(body==AppAropInt.CODE_SING_UP_SUCCESS.value) {
                        showMessage(dialog.context, tag, AppProp.STATUS_MESSAGE_SING_UP_SUCCESS.value, "HTTP Code: ${it.raw().code()}")
                    }
                }
                dialog.dismiss()
            }

            AppProp.SingletonObject.userApi?.signUpPost(AppProp.SingletonObject.userVo!!)?.enqueue(
                CallBackImpl(
                    dialog.context,
                    tag,
                    responseTask
                )
            )
        }

        dialogInputCancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    /**
    * @author: JiMinLee
    * @param: inputEmail:String,inputPw:String,inputPw2:String,dialogContext:Context
    * @return: Boolean
    * @description: 입력 값 유효성 검사
    **/
    private fun inputCheck(inputEmail:String,inputPw:String,inputPw2:String,dialogContext:Context):Boolean {
        if(isNullItems(inputEmail,inputPw,inputPw2) && isEmptyItems(inputEmail,inputPw,inputPw2)) {
            showMessage(dialogContext,tag,AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value)
            return false
        }

        if (isExists) {
            showMessage(dialogContext,tag, AppProp.STATUS_MESSAGE_ALREADY_EXISTS_NO_CHECK.value)
            return false
        }

        if(!inputPw.equals(inputPw2)) {
            showMessage(dialogContext,tag,AppProp.STATUS_MESSAGE_PASSWORD_NOT_PAIR.value)
            return false
        }

        return true
    }
}
