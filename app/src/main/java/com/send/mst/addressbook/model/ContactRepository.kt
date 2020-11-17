package com.send.mst.addressbook.model

import android.content.Context
import android.provider.ContactsContract
import android.util.Log

/**
 * @author JiMinLee
 * @desc 주소록을 가져옴
 **/
class ContactRepository(val context: Context) {
    private val tag = this.javaClass.toString()

    fun fetchContactList(): AddressBookListModel{
        val addressBookArrayList = ArrayList<AddressBookModel>()

        var name:String?=""
        var phoneNumber:String?=""

        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

        while(cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val addressBookModel= AddressBookModel("","",name,phoneNumber,"","")
            addressBookArrayList.add(addressBookModel)

            Log.d(tag,"$name, $phoneNumber")
        }
        cursor.close()

    return AddressBookListModel(addressBookArrayList)
    }
}