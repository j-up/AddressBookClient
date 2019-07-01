package com.send.mst.addressbook.common.utils

import android.app.Dialog
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.send.mst.addressbook.R
import android.view.WindowManager
import com.send.mst.addressbook.common.utils.Utils.Companion.isNotNull
import com.send.mst.addressbook.common.utils.Utils.Companion.isValidEmail

/**
 * @author JiMinLee
 * @desc
 **/

class MyDialog {
    val TAG = this.javaClass.toString()
    var dialog:Dialog? = null

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
        var isNotExists:Boolean=false
        var isEmail:Boolean = false

        dialogInputExistsButton.setOnClickListener {
            Utils.onVibe(dialog.context,100L)
            val inputEmail:String? = dialogInputEmailEditText.text.toString()
            inputEmail?.let { email -> isEmail=isValidEmail(email) }

            if (isEmail) {
                // Todo email 형식이 맞을때 중복검사
                isNotExists = true
                Utils.showMessage(dialog.context,TAG,"확인 완료","")
            } else {
                Utils.showMessage(dialog.context,TAG,"올바른 email형식을 입력하세요","Is not email")
            }


        }

        dialogInputSignUpButton.setOnClickListener {
            Utils.onVibe(dialog.context,100L)
            val inputEmail:String? = dialogInputEmailEditText.text.toString()
            val inputPw:String?= dialogInputPwEditText.text.toString()
            val inputPw2:String? = dialogInputPw2EditText.text.toString()

            Log.d(TAG,dialogInputEmailEditText.text.toString())
            Log.d(TAG,dialogInputPwEditText.text.toString())
            Log.d(TAG,dialogInputPw2EditText.text.toString())

            if (isNotNull(inputEmail,inputPw,inputPw2)) {
                if (isNotExists) {
                    Utils.showMessage(dialog.context, TAG, "회원가입 완료", "User signUp success")
                    dialog.dismiss()
                } else {
                    Utils.showMessage(dialog.context,TAG,"중복 검사를 실행하세요","중복검사 미실행 ")
                }

            } else {
                Utils.showMessage(dialog.context,TAG,"공백을 입력해주세요","Input is null")
            }

        }

        dialogInputCancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }



}