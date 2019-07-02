package com.send.mst.addressbook.domain.vo.addressBook

import java.io.Serializable

class AddressBookVO (var id: String?, var name:String?,var phone:String?,var userGroup:String?,var bookmark:String?): Serializable {
    private val serialVersionUID = 2421823L
    fun toAllString():String {
        return "$id $name $phone $userGroup $bookmark"
    }
}