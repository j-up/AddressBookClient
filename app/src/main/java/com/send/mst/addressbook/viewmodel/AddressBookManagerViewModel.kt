package com.send.mst.addressbook.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.send.mst.addressbook.common.utils.SingleLiveEvent
import com.send.mst.addressbook.common.utils.SingletonObject
import com.send.mst.addressbook.model.AddressBookModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author JiMinLee
 * @desc
 **/
class AddressBookManagerViewModel:ViewModel() {
    private val _onUploadClick = SingleLiveEvent<Boolean>()
    private val _onDownloadClick = SingleLiveEvent<Boolean>()
    private val _error = SingleLiveEvent<String>()

    val onUploadClick: LiveData<Boolean> get() = _onUploadClick
    val onDownloadClick: LiveData<Boolean> get() = _onDownloadClick
    val error: LiveData<String> get() = _error

    fun onUploadClick() {
        _onUploadClick.call()
    }

    fun onDownloadClick() {
        doAddressBookDownload()
        _onDownloadClick.call()
    }

    private fun doAddressBookDownload() {
        SingletonObject.addressBookModel = AddressBookModel(SingletonObject.idIndex.toString(),"","","","")
        SingletonObject.apiServer.getAddressBook(SingletonObject.addressBookModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.addressBookListModel?.let{ addressBookListModel ->
                    for(addressBookModel in addressBookListModel) {
                        Log.d("AddressBookModel",addressBookModel.toString())
                    }
                }
            },{
                _error.value = it.message
            })
    }
}