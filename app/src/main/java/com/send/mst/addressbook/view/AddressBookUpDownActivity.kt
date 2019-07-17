package com.send.mst.addressbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.send.mst.addressbook.R
import com.send.mst.addressbook.common.utils.SingletonObject
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.model.AddressBookListModel
import com.send.mst.addressbook.model.AddressBookModel
import retrofit2.Response

/**
 * @author JiMinLee
 * @description 주소록 업로드/다운로드를 할 수 있는 액티비티
 **/

class AddressBookUpDownActivity : AppCompatActivity(), View.OnClickListener {
    private val tag = this.javaClass.toString()
    private lateinit var addressUpLoadButton: Button
    private lateinit var addressDownloadButton: Button
    private val dummyData = AddressBookModel("27", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_book_up_down)

        addressUpLoadButton = findViewById(R.id.button_upload)
        addressDownloadButton = findViewById(R.id.button_download)

        addressUpLoadButton.setOnClickListener(this)
        addressDownloadButton.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        Utils.onVibe(applicationContext,100L)

        when(v.id) {
            R.id.button_upload -> {

            }

            R.id.button_download -> {/*
                // Todo 다운중 프로그래스바 처리
                val responseTask:(response: Response<AddressBookListModel>) -> Unit = {
                    it.body()?.addressBookListModel?.let{ addressBookModelArray ->
                        for(addressBookModel in addressBookModelArray) {
                            Log.d(tag,addressBookModel.toString())
                        }
                    }
                }

                SingletonObject.apiServer?.getAddressBook(dummyData)?.enqueue(
                    CallBackImpl(
                        this,
                        tag,
                        responseTask
                    )
                )*/
            }
        }
    }

}
