package com.send.mst.addressbook.domain.vo.addressBook

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressBookListVO () {
    @SerializedName("addressBookVoList")
    @Expose
    val addressBookListVo:Array<AddressBookVO> = arrayOf()
}