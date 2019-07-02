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
    val TAG = this.javaClass.toString()
    var dialog:Dialog? = null

    var isExists:Boolean= true
    var isEmail:Boolean = false

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
                showMessage(dialog.context,TAG,"올바른 email 형식을 입력하세요","Is not email")
                return@setOnClickListener
            }

            if(false) {
                // Todo 중복검사
                showMessage(dialog.context,TAG,"이미 사용중인 계정입니다","email is exists")
            } else {
                isExists = false
                dialogInputEmailEditText.isEnabled=false
                dialogInputEmailEditText.background=null

                showMessage(dialog.context,TAG,"사용 가능한 계정입니다","email is pass")
                dialogInputExistsButton.visibility = View.INVISIBLE
            }
        }
        // 회원가입
        dialogInputSignUpButton.setOnClickListener {
            Utils.onVibe(dialog.context,100L)
            val inputEmail:String = dialogInputEmailEditText.text?.toString()?:""
            val inputPw:String= dialogInputPwEditText.text?.toString()?:""
            val inputPw2:String = dialogInputPw2EditText.text?.toString()?:""

            if(!inputCheck(inputEmail,inputPw,inputPw2,dialog.context)) {
                return@setOnClickListener
            }

            Log.d(TAG,dialogInputEmailEditText.text.toString())
            Log.d(TAG,dialogInputPwEditText.text.toString())
            Log.d(TAG,dialogInputPw2EditText.text.toString())

            val userVo = UserVO(inputEmail,inputPw)
            AppProp.singletonObject.userApi = AppProp.singletonObject.retrofit.let {
                it.create(UserAPI::class.java)
            }
            val responseTask:(response: Response<Int>) -> Unit = {
                it.body()?:showMessage(dialog.context, TAG, "가입 실패", "HTTP Code: ${it.raw().code()}")

                it.body().let{ body ->
                    Log.d(TAG,"body: ${body.toString()}")
                    if(body==1) {
                        showMessage(dialog.context, TAG, "회원가입 완료", "User signUp success")
                    }
                }
                dialog.dismiss()
            }

            AppProp.singletonObject.userApi?.signupPost(userVo)?.enqueue(
                CallBackImpl(
                    dialog.context,
                    TAG,
                    responseTask
                )
            )
        }

        dialogInputCancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun inputCheck(inputEmail:String,inputPw:String,inputPw2:String,dialogContext:Context):Boolean {
        if(isNullItems(inputEmail,inputPw,inputPw2) && isEmptyItems(inputEmail,inputPw,inputPw2)) {
            showMessage(dialogContext,TAG,"공백을 입력해주세요","Input is null")
            return false
        }

        if (isExists) {
            showMessage(dialogContext,TAG,"중복 검사를 실행하세요","중복검사 미실행 ")
            return false
        }

        if(!inputPw.equals(inputPw2)) {
            showMessage(dialogContext,TAG,"비밀번호 불일치","")
            return false
        }

        return true
    }
}
