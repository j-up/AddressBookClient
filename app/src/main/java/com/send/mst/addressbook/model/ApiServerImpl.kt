package com.send.mst.addressbook.model

import com.send.mst.addressbook.common.network.api.ApiServer
import retrofit2.Call

/**
 * @author JiMinLee
 * @desc
 **/
class ApiServerImpl : ApiServer {

    override fun signUpPost(userVO: UserModel): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun idCheckPost(userVO: UserModel): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginPost(userVO: UserModel): Call<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    override fun getAddressBook(addressBookModel: AddressBookModel): Call<AddressBookListModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}