package com.send.mst.addressbook

import android.app.Application
import com.send.mst.addressbook.common.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author JiMinLee
 * @desc
 **/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

}
