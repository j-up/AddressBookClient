package com.send.mst.addressbook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

/**
 * @author: JiMinLee
 * @description: 메인 액티비티
 **/

class MainActivity : AppCompatActivity(),View.OnClickListener  {
    val TAG = this.javaClass.toString()
    lateinit var loginButton: Button
    lateinit var signUpButton: Button
    lateinit var naverLoginButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.button_login) as Button
        signUpButton = findViewById(R.id.button_signUp) as Button
        naverLoginButton = findViewById(R.id.button_naverLogin) as ImageButton

        loginButton.setOnClickListener(this)
        signUpButton.setOnClickListener(this)
        naverLoginButton.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        var intent: Intent
        // Todo network 유효성 처리

        when(v.id) {
            R.id.button_login -> {
                //ToDo 로그인처리
                intent= Intent(this, AddressBookUpDownActivity::class.java)
                startActivity(intent)
                this.finish()
            }

            R.id.button_signUp -> {
                //Todo 회원가입처리
            }

            R.id.button_naverLogin -> {
                //Todo 네이버로그인처리
            }

            //Todo 다른 sns 로그인생성

        }
    }
}
