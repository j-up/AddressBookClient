package com.send.mst.addressbook.common.utils

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import java.util.regex.Pattern

/**
 * @author: JiMinLee
 * @description: 각종 유틸을 모아놓은 클래스
 **/

class Utils() {

    companion object{
        /**
         * @author: JiMinLee
         * @param: context:Context, 로그 태그:String, 사용자에게 보여줄 텍스트와 로그에 기록할 텍스트:String
         * @description: 메시지를 보여주고 로그를 기록
         **/
        fun showMessage(context: Context, tag: String, text: String) {
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show()
            Log.d(tag,text)
        }

        /**
         * @author: JiMinLee
         * @param: context:Context, 로그 태그:String, 사용자에게 보여줄 텍스트:String, 로그에 기록할 텍스트:String
         * @return:
         * @description: 메시지를 보여주고 로그를 기록
         **/
        fun showMessage(context: Context, tag: String, showText: String, logText: String) {
            Toast.makeText(context,showText, Toast.LENGTH_SHORT).show()
            Log.d(tag,logText)
        }

        /**
         * @author: JiMinLee
         * @param: context:Context, 진동시간:Long
         * @return:
         * @description: 진동시간만큼 진동을 울리게함
         **/
        fun onVibe(context: Context,delay: Long) {
            AppProp.SingletonObject.vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            AppProp.SingletonObject.vibrator.let {
                it?.vibrate(VibrationEffect.createOneShot(delay,VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }

        /**
        * @author: JiMinLee
        * @param: elements
        * @return: Null여부 :boolean
        * @description: 여러 아이템들중 Null 있는지
        **/
        fun <T> isNullItems(vararg elements: T): Boolean {
            elements.forEach {
                if (it == null) {
                    return true
                }
            }
            return false
        }

        /**
         * @author: JiMinLee
         * @param: elements
         * @return: 공백여부 :boolean
         * @description: 여러 아이템들중 Empty 있는지
         **/
        fun isEmptyItems(vararg elements: String): Boolean {
            elements.forEach {
                if (it.isEmpty()) {
                    return true
                }
            }
            return false
        }

        /**
        * @author: JiMinLee
        * @param: email:String
        * @return: boolean
        * @description: param의 값이 email형식이 맞는지 테스트
        **/
        fun isValidEmail(email: String): Boolean {
            var err = false
            val regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
            val p = Pattern.compile(regex)
            val m = p.matcher(email)
            if (m.matches()) {
                err = true
            }
            return err
        }


    }
}