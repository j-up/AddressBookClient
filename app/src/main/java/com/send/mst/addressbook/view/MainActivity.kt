package com.send.mst.addressbook.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.utils.*
import com.send.mst.addressbook.databinding.ActivityMainBinding
import com.send.mst.addressbook.model.UserModel
import com.send.mst.addressbook.viewmodel.MainViewModel
import retrofit2.Response

/**
 * @author: JiMinLee
 * @description: 메인 액티비티
 **/

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    private val tag = this.javaClass.toString()
    private val myDialog = MyDialog()
    private val mainActivity:Activity = this

    private lateinit var signUpButton: Button
    private lateinit var idEditText: EditText
    private lateinit var pwEditText: EditText
    private lateinit var naverLoginButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel:MainViewModel = MainViewModel()

        mainViewModel.onLoginClick.observe(this, Observer{
            Toast.makeText(this,"클릭 " + it,Toast.LENGTH_SHORT).show()
        })

        mainViewModel.isLoginState.observe(this, Observer {
            if(it) {
                Toast.makeText(this, "로그인성공",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"로그인성공",Toast.LENGTH_SHORT).show()
            }
        })

        mainViewModel.onSignUpClick.observe(this,Observer{
            Toast.makeText(this,"클릭 " + it,Toast.LENGTH_SHORT).show()
            //myDialog.createCustomDialog(this, R.layout.custom_dialog_signup)
        })


        binding.viewModel=mainViewModel
        binding.setLifecycleOwner(this@MainActivity)


        idEditText = findViewById<EditText>(R.id.editText_email)
        pwEditText = findViewById<EditText>(R.id.editText_pw)


       // idEditText.setText("test@test.com")
       // pwEditText.setText("test")
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
    }

    override fun onClick(v: View) {
        var intent: Intent

        when(v.id) {
            /*
            R.id.button_login -> {
                //ToDo 쿠키처리 -> sns 로그인 처리

            R.id.button_signUp -> {
                myDialog.createCustomDialog(this, R.layout.custom_dialog_signup)
            }
*/
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
        AppProp.SingletonObject.userModel = UserModel(inputEmail, inputPw)

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
            AppProp.SingletonObject.apiServer?.loginPost(AppProp.SingletonObject.userModel!!)?.enqueue(
                CallBackImpl(
                    this,
                    tag,
                    responseTask
                )
            )
    }
}
