package com.send.mst.addressbook.common.utils

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast

/**
 * @author: JiMinLee
 * @description: 각종 유틸을 모아놓은 클래스
 **/

class Utils() {

    companion object{

        /**
         * @author: JiMinLee
         * @param: context:Context, 로그 태그:String, 사용자에게 보여줄 텍스트:String, 로그에 기록할 텍스트:String
         * @return:
         * @description: 에러가 날경우 메시지를 보여주고 로그를 기록
         **/
        fun onUpdateError(context: Context, TAG: String, showText: String, logText: String) {
            Toast.makeText(context,showText, Toast.LENGTH_SHORT).show()
            Log.d(TAG,logText)
        }

        /**
         * @author: JiMinLee
         * @param: context:Context, 진동시간:Long
         * @return:
         * @description: 진동시간만큼 진동을 울리게함
         **/
        fun onVibe(context: Context,delay: Long) {
            AppProp.singletonObject.vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            AppProp.singletonObject.vibrator.let {
                it?.vibrate(VibrationEffect.createOneShot(delay,VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }
    }
}