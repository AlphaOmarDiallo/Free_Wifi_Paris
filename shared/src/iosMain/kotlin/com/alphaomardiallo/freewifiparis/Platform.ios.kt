package com.alphaomardiallo.freewifiparis

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single<HttpClientEngine> { Darwin.create() }
}