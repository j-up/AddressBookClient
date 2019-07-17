package com.send.mst.addressbook.model

import java.io.Serializable

/**
 * @author JiMinLee
 * @desc
 **/
data class UserModel(var email:String, var password:String):Serializable {
    private val serialVersionUID = 14235328L
}