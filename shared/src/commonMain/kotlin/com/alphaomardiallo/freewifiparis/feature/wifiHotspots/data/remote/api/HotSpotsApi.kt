package com.alphaomardiallo.freewifiparis.feature.wifiHotspots.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import org.koin.core.component.KoinComponent

class HotSpotsApi(private val client: HttpClient) : KoinComponent {

    suspend fun fetchHotSpots(postCode: String = "75001"): HttpResponse {
        return client.get {
            url("$BASE_URL$URL")
            parameter("limit", "100")
            parameter("refine", "etat2:Opérationnel")
            parameter("refine", "cp:$postCode")
        }
    }

    private companion object {
        const val BASE_URL = "https://opendata.paris.fr/api/explore/v2.1/catalog/datasets/"
        const val URL = "sites-disposant-du-service-paris-wi-fi/records?"
    }
}
