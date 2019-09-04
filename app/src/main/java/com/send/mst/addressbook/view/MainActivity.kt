package com.send.mst.addressbook.view

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.core.content.ContextCompat
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
    private val contactPermission = 9000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel = MainViewModel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), contactPermission)
            }
        }


        mainViewModel.editTextId.value = "test@test.com"
        mainViewModel.editTextPw.value = "test"

        mainViewModel.onLoginClick.observe(this, Observer{
            Utils.showMessage(applicationContext, tag, AppProp.STATUS_MESSAGE_LOGIN_SUCCESS.value)
            intent=Intent(this, AddressBookManagerActivity::class.java)
            startActivity(intent)
            // mainActivity.finish()
        })

        mainViewModel.error.observe(this, Observer {
            Utils.showMessage(applicationContext, tag, it)
        })

        mainViewModel.onSignUpClick.observe(this,Observer{
            myDialog.createCustomDialog(this, R.layout.custom_dialog_signup)
        })

        binding.mainViewModel=mainViewModel
        binding.lifecycleOwner=this@MainActivity
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (contactPermission == requestCode) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                finish()
            }
        }
    }
}
