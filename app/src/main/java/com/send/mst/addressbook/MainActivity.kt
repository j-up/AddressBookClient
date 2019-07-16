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
import com.send.mst.addressbook.common.di.HelloRepository
import com.send.mst.addressbook.common.di.MySimplePresenter
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.network.api.ServerAPI
import com.send.mst.addressbook.common.utils.*
import com.send.mst.addressbook.domain.vo.user.UserVO
import org.koin.android.ext.android.inject
import retrofit2.Response

/**
 * @author: JiMinLee
 * @description: 메인 액티비티
 **/

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    private val tag = this.javaClass.toString()
    private val myDialog = MyDialog()
    private val mainActivity:Activity = this

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var idEditText: EditText
    private lateinit var pwEditText: EditText
    private lateinit var naverLoginButton: ImageButton

    val firstPresenter: MySimplePresenter by inject()
    val firstPresenter2: HelloRepository by inject()
    val utils: Utils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = findViewById<Button>(R.id.button_login)
        signUpButton = findViewById<Button>(R.id.button_signUp)
        naverLoginButton = findViewById<ImageButton>(R.id.button_naverLogin) as ImageButton
        idEditText = findViewById<EditText>(R.id.editText_email)
        pwEditText = findViewById<EditText>(R.id.editText_pw)

        loginButton.setOnClickListener(this)
        signUpButton.setOnClickListener(this)
        naverLoginButton.setOnClickListener(this)

        idEditText.setText("test@test.com")
        pwEditText.setText("test")
        pwEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val inputEmail= idEditText.text.toString()
                val inputPw = pwEditText.text.toString()

                if(!Utils.isEmptyItems(inputEmail,inputPw) || !Utils.isNullItems(inputEmail,inputPw)) {
                    doLogin(idEditText.text.toString(),pwEditText.text.toString())
                }
                return@OnKeyListener true
            }
            false
        })
        Log.d(tag,"ㅁMySimplePresenter: "+firstPresenter.sayHello())
        Log.d(tag,"ㅁHelloRepositoryImpl: "+firstPresenter2.giveHello())

    }

    override fun onClick(v: View) {
        var intent: Intent

        when(v.id) {
            R.id.button_login -> {
                //ToDo 쿠키처리 -> sns 로그인 처리
                val inputEmail= idEditText.text.toString()
                val inputPw = pwEditText.text.toString()

                doLogin(idEditText.text.toString(),pwEditText.text.toString())
            }

            R.id.button_signUp -> {
                myDialog.createCustomDialog(this,R.layout.custom_dialog_signup)
            }

            R.id.button_naverLogin -> {

            }

            //Todo 다른 sns 로그인생성
        }
    }

    private fun doLogin(inputEmail:String,inputPw:String) {
        if(Utils.isEmptyItems(inputEmail,inputPw) || Utils.isNullItems(inputEmail,inputPw)) {
            Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_INPUT_IS_NULL.value)
            return
        }
        AppProp.SingletonObject.userVo = UserVO(inputEmail, inputPw)

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
                   // mainActivity.finish()
                } else {
                    Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_LOGIN_FAIL.value)
                }
            }
        }
            AppProp.SingletonObject.serverApi?.loginPost(AppProp.SingletonObject.userVo!!)?.enqueue(
                CallBackImpl(
                    this,
                    tag,
                    responseTask
                )
            )
    }
}
