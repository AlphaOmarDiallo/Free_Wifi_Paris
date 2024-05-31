package com.alphaomardiallo.freewifiparis.di

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.repository.WifiHotspotsRepositoryImpl
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository.WifiHotspotsRepository
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
import com.alphaomardiallo.freewifiparis.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module


val repositories = module {
    singleOf(::WifiHotspotsRepositoryImpl).bind<WifiHotspotsRepository>()
}

val useCases = module {
    factory { GetWifiHotspotsUseCase(get()) }
}

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            platformModule(),
            repositories,
            useCases
        )
    }

fun commonModule(enableNetworkLogs: Boolean = false) = module {
    singleOf(::createJson)
    single {
        createHttpClient(
            httpClientEngine = get(),
            json = get(),
            enableNetworkLogs = enableNetworkLogs
        )
    }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }

        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }