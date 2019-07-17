package com.send.mst.addressbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.databinding.ActivityAddressBookManagerBinding
import com.send.mst.addressbook.databinding.ActivityMainBinding
import com.send.mst.addressbook.model.AddressBookModel
import com.send.mst.addressbook.viewmodel.AddressBookManagerViewModel
import com.send.mst.addressbook.viewmodel.MainViewModel

/**
 * @author JiMinLee
 * @description 주소록 업로드/다운로드를 할 수 있는 액티비티
 **/

class AddressBookManagerActivity : AppCompatActivity() {
    private val tag = this.javaClass.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAddressBookManagerBinding>(this, R.layout.activity_address_book_manager)
        val addressBookManagerViewModel = AddressBookManagerViewModel()

        addressBookManagerViewModel.onDownloadClick.observe(this, Observer{
            Utils.showMessage(this,tag,"클릭")
        })

        addressBookManagerViewModel.error.observe(this, Observer {
            Utils.showMessage(applicationContext, tag, it)
        })

        binding.addressBookManagerViewModel=addressBookManagerViewModel
        binding.lifecycleOwner=this@AddressBookManagerActivity

    }
}
