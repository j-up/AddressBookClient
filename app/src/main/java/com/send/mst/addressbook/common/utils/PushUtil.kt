package com.send.mst.addressbook.common.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * @author JiMinLee
 * @desc 각종 메시지알림을 모아놓은 클래스
 **/

class PushUtil() {

    companion object{
        fun onUpdateError(context: Context, TAG: String, showText: String, logText: String) {
            Toast.makeText(context,showText, Toast.LENGTH_SHORT).show()
            Log.d(TAG,logText)
        }
    }
}