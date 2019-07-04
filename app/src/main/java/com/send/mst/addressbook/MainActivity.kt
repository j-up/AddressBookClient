package com.send.mst.addressbook

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.network.api.user.UserAPI
import com.send.mst.addressbook.common.utils.*
import com.send.mst.addressbook.domain.vo.user.UserVO
import retrofit2.Response

/**
 * @author: JiMinLee
 * @description: 메인 액티비티
 **/

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    private val tag = this.javaClass.toString()
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var idEditText: EditText
    private lateinit var pwEditTExt: EditText
    private lateinit var naverLoginButton: ImageButton
    private val myDialog = MyDialog()
    private val mainActivity:Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.button_login) as Button
        signUpButton = findViewById(R.id.button_signUp) as Button
        naverLoginButton = findViewById(R.id.button_naverLogin) as ImageButton
        idEditText = findViewById(R.id.editText_email) as EditText
        pwEditTExt = findViewById(R.id.editText_pw) as EditText

        loginButton.setOnClickListener(this)
        signUpButton.setOnClickListener(this)
        naverLoginButton.setOnClickListener(this)

        pwEditTExt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val inputEmail= idEditText.text.toString()
                val inputPw = pwEditTExt.text.toString()

                if(Utils.isEmptyItems(inputEmail,inputPw) || Utils.isNullItems(inputEmail,inputPw)) {
                    return@OnKeyListener true
                }

                doLogin(idEditText.text.toString(),pwEditTExt.text.toString())
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onClick(v: View) {
        var intent: Intent
        // Todo network 유효성 처리

        when(v.id) {
            R.id.button_login -> {
                //ToDo 로그인처리, 쿠키처리

                val inputEmail= idEditText.text.toString()
                val inputPw = pwEditTExt.text.toString()

                if(Utils.isEmptyItems(inputEmail,inputPw) || Utils.isNullItems(inputEmail,inputPw)) {
                    Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value)
                    return
                }

                doLogin(idEditText.text.toString(),pwEditTExt.text.toString())
                this.finish()

            }

            R.id.button_signUp -> {
                myDialog.createCustomDialog(this,R.layout.custom_dialog_signup)

            }

            R.id.button_naverLogin -> {
                //Todo 네이버로그인처리
            }

            //Todo 다른 sns 로그인생성

        }
    }

    private fun doLogin(inputEmail:String,inputPw:String) {
        AppProp.SingletonObject.userVo = UserVO(inputEmail, inputPw)
        AppProp.SingletonObject.userApi = AppProp.SingletonObject.retrofit.let {
            it.create(UserAPI::class.java)
        }

        val responseTask: (response: Response<Int>) -> Unit = {
            if (it.raw().code() != 200) {
                Utils.showMessage(
                    applicationContext,
                    tag,
                    AppProp.STATUS_MESSAGE_INTERNAL_SERVER_ERROR.value,
                    "HTTP Code: ${it.raw().code()}"
                )
            }
            it.body().let { body ->
                Log.d(tag, "body: ${body.toString()}")
                // 로그인 성공
                if (body == AppPropInt.CODE_LOGIN_SUCCESS.value) {
                    Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_LOGIN_SUCCESS.value)
                    intent= Intent(this, AddressBookUpDownActivity::class.java)
                    startActivity(intent)
                    mainActivity.finish()
                } else {
                    Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_LOGIN_FAIL.value)
                }
            }
        }
            AppProp.SingletonObject.userApi?.loginPost(AppProp.SingletonObject.userVo!!)?.enqueue(
                CallBackImpl(
                    this,
                    tag,
                    responseTask
                )
            )
    }
}
