package com.alphaomardiallo.freewifiparis.android

import android.app.Application
import com.alphaomardiallo.freewifiparis.android.di.wifiHotSpotsModule
import com.alphaomardiallo.freewifiparis.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class FreeWifiParisApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@FreeWifiParisApplication)
            modules(wifiHotSpotsModule)
        }
    }
}
