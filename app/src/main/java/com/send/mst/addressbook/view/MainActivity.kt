package com.send.mst.addressbook.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.utils.*
import com.send.mst.addressbook.databinding.ActivityMainBinding
import com.send.mst.addressbook.viewmodel.MainViewModel

/**
 * @author: JiMinLee
 * @description: 메인 액티비티
 **/

class MainActivity : AppCompatActivity() {
    private val tag = this.javaClass.toString()
    private val myDialog = MyDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel:MainViewModel = MainViewModel()

        mainViewModel.editTextId.value = "test@test.com"
        mainViewModel.editTextPw.value = "test"

        mainViewModel.onLoginClick.observe(this, Observer{ loginState ->
            if(loginState) {
                Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_LOGIN_SUCCESS.value)
                intent=Intent(this, AddressBookUpDownActivity::class.java)
                startActivity(intent)
                // mainActivity.finish()
            }
        })

        mainViewModel.error.observe(this, Observer {
            Utils.showMessage(applicationContext, tag, it)
        })

        mainViewModel.onSignUpClick.observe(this,Observer{
            myDialog.createCustomDialog(this, R.layout.custom_dialog_signup)
        })

        binding.viewModel=mainViewModel
        binding.lifecycleOwner=this@MainActivity
    }
}
