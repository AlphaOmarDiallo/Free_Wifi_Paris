package com.alphaomardiallo.freewifiparis

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { Android.create() }
}