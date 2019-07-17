package com.send.mst.addressbook.model

import java.io.Serializable

data class AddressBookModel (var id: String?, var name:String?, var phone:String?, var userGroup:String?, var bookmark:String?): Serializable {
    private val serialVersionUID = 2421823L

    override fun toString():String {
        return "$id $name $phone $userGroup $bookmark"
    }
}