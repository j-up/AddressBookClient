package com.send.mst.addressbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.send.mst.addressbook.common.network.api.addrssBook.AddressBookAPI
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.network.CallBackImpl
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import retrofit2.Response

/**
 * @author JiMinLee
 * @description 주소록 업로드/다운로드를 할 수 있는 액티비티
 **/

class AddressBookUpDownActivity : AppCompatActivity(), View.OnClickListener {
    private val tag = this.javaClass.toString()
    private lateinit var addressUpLoadButton: Button
    private lateinit var addressDownloadButton: Button
    private val dummyData = AddressBookVO("6","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_book_up_down)

        addressUpLoadButton = findViewById(R.id.button_upload)
        addressDownloadButton = findViewById(R.id.button_download)

        addressUpLoadButton.setOnClickListener(this)
        addressDownloadButton.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        AppProp.SingletonObject.addressBookApi = AppProp.SingletonObject.retrofit.let {
            it.create(AddressBookAPI::class.java)
        }
        Utils.onVibe(applicationContext,100L)

        when(v.id) {
            R.id.button_upload -> {

            }

            R.id.button_download -> {
                // Todo 다운중 프로그래스바 처리
                val responseTask:(response: Response<AddressBookListVO>) -> Unit = {
                    it.body()?.addressBookListVo?.let{ addressBookVoArray ->
                        for(addressBookVo in addressBookVoArray) {
                            Log.d(tag,addressBookVo.toAllString())
                        }
                    }
                }

                AppProp.SingletonObject.addressBookApi?.getAddressBook(dummyData)?.enqueue(
                    CallBackImpl(
                        this,
                        tag,
                        responseTask
                    )
                )
            }
        }
    }

}
