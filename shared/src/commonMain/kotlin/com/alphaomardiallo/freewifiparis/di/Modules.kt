package com.alphaomardiallo.freewifiparis.di

import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.api.HotSpotsApi
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.datasource.HotSpotsDataSource
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.repository.WifiHotspotsRepositoryImpl
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.repository.WifiHotspotsRepository
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.GetWifiHotspotsUseCase
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.domain.usecase.ToHotSpotsMarkerUiUseCase
import com.alphaomardiallo.freewifiparis.feature.wifiHotspots.presentation.WifiHotSpotsViewModel
import com.alphaomardiallo.freewifiparis.platformModule
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

val apis = module {
    singleOf(::HotSpotsApi)
}

val dataSources = module {
    singleOf(::HotSpotsDataSource)
}

val repositories = module {
    singleOf(::WifiHotspotsRepositoryImpl).bind<WifiHotspotsRepository>()
}

val viewModels = module {
    factoryOf(::WifiHotSpotsViewModel)
}

val useCases = module {
    factory { GetWifiHotspotsUseCase(get()) }
    factory { ToHotSpotsMarkerUiUseCase() }
}

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            platformModule(),
            apis,
            dataSources,
            repositories,
            viewModels,
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
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d("[HTTP CALL] $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
