package com.send.mst.addressbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.send.mst.addressbook.common.network.api.AddressBookAPI
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.utils.Utils
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookListVO
import com.send.mst.addressbook.domain.vo.addressBook.AddressBookVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author JiMinLee
 * @description 주소록 업로드/다운로드를 할 수 있는 액티비티
 **/

class AddressBookUpDownActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = this.javaClass.toString()
    lateinit var addressUpLoadButton: Button
    lateinit var addressDownloadButton: Button
    val dummyData = AddressBookVO().apply {
        id="6"
        name=""
        phone=""
        userGroup=""
        bookmark=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_book_up_down)

        addressUpLoadButton = findViewById(R.id.button_upload)
        addressDownloadButton = findViewById(R.id.button_download)

        addressUpLoadButton.setOnClickListener(this)
        addressDownloadButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        AppProp.singletonObject.addressBookApi = AppProp.singletonObject.retrofit.let {
            it.create(AddressBookAPI::class.java)
        }
        // Todo 진동알림
        Utils.onVibe(applicationContext,100L)

        when(v.id) {
            R.id.button_upload -> {

            }
            R.id.button_download -> {
                // Todo 다운중 프로그래스바 처리
                AppProp.singletonObject.addressBookApi?.postAddressBook(dummyData)?.enqueue(object : Callback<AddressBookListVO> {
                    override fun onResponse(call: Call<AddressBookListVO>, response: Response<AddressBookListVO>) {
                        val addressBookListVo = response.body()
                        addressBookListVo.let {
                            for(addressBookVo in it!!.addressBookListVo) {
                                Log.d(TAG,addressBookVo.toAllString())
                            }
                        }
                    }

                    override fun onFailure(call: Call<AddressBookListVO>, t: Throwable) {
                        Utils.onUpdateError(applicationContext,TAG,"연결 실패",t.message.toString())
                    }
                })
            }
        }
    }
}
