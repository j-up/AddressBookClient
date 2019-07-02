package com.send.mst.addressbook.common.network

import android.content.Context
import com.send.mst.addressbook.common.utils.AppProp
import com.send.mst.addressbook.common.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author JiMinLee
 * @desc CallBack 인터페이스 구현 클래스
 **/
class CallBackImpl<T>(private val context: Context, private val tag:String,
                      val responseTask:(response: Response<T>) -> Unit): Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T> ) {
            responseTask(response)
        }
        override fun onFailure(call: Call<T>, t: Throwable) {
            Utils.showMessage(context, tag, AppProp.STATUS_MESSAGE_CONNECT_FAIL.value, "ErrorMsg: ${t.message.toString()}"
            )
        }
}