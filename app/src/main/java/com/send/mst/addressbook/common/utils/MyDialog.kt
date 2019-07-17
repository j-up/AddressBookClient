package com.send.mst.addressbook.common.utils

import com.send.mst.addressbook.R
import android.app.Dialog
import android.content.Context

import android.view.WindowManager
import android.view.Window
import com.send.mst.addressbook.dialog.SignUpCustomDialog



/**
 * @author JiMinLee
 * @desc
 **/

class MyDialog {
    private val tag = this.javaClass.toString()
    private var dialog:Dialog? = null

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

                val lp = WindowManager.LayoutParams().apply {
                    copyFrom(it.window.attributes)
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.WRAP_CONTENT
                }

                val window = it.window
                window.attributes = lp

                it.show()
            }
            when(resId) {
                R.layout.custom_dialog_signup -> {
                    dialog?.let {
                        val signUpCustomDialog = SignUpCustomDialog(it)
                        signUpCustomDialog.taskSignUp()
                    }
                }
            }
        }
}
