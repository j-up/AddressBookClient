package com.send.mst.addressbook.domain.vo.addressBook

class AddressBookVO () {
    var id: String = ""
    var name: String = ""
    var phone: String = ""
    var userGroup: String = ""
    var bookmark: String = ""

    fun toAllString():String {
        return "$id $name $phone $userGroup $bookmark"
    }
}