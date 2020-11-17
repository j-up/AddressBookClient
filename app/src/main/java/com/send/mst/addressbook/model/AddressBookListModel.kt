package com.send.mst.addressbook.model

import java.io.Serializable

data class AddressBookListModel (val addressBookListModel:ArrayList<AddressBookModel>) : Serializable {
    private val serialVersionUID = 88593112L

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}