package com.send.mst.addressbook.view

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.databinding.ActivityAddressBookManagerBinding
import com.send.mst.addressbook.model.ContactRepository
import com.send.mst.addressbook.viewmodel.AddressBookManagerViewModel

/**
 * @author JiMinLee
 * @description 주소록 업로드/다운로드를 할 수 있는 액티비티
 **/

class AddressBookManagerActivity : AppCompatActivity() {
    private val tag = this.javaClass.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAddressBookManagerBinding>(this, R.layout.activity_address_book_manager)
        val contactRepository = ContactRepository(applicationContext)
        val addressBookManagerViewModel = AddressBookManagerViewModel(contactRepository)

        addressBookManagerViewModel.onUploadClick.observe(this, Observer{
            Utils.showMessage(this,tag,"업로드 클릭")
        })

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
