package com.alphaomardiallo.freewifiparis.android

import android.app.Application
import com.alphaomardiallo.freewifiparis.android.di.wifiHotSpotsModule
import com.alphaomardiallo.freewifiparis.di.initKoin
import io.github.aakira.napier.BuildConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class FreeWifiParisApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin(enableNetworkLogs = BuildConfig.DEBUG) {
            androidLogger()
            androidContext(this@FreeWifiParisApplication)
            modules(wifiHotSpotsModule)
        }

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
    }
}
