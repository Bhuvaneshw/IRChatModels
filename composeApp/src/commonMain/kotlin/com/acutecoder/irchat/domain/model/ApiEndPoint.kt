package com.acutecoder.irchat.domain.model

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

data class ApiEndPoint(
    private val ip: String,
    private val port: String
) {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private fun connectionString() = "http://$ip:$port"

    fun routeConnect() = connectionString() + "/connect"

    fun routeListModels() = connectionString() + "/list_models"

    fun routePredict() = connectionString() + "/predict"

}
