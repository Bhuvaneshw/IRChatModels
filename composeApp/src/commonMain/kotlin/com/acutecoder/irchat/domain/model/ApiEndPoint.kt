package com.acutecoder.irchat.domain.model

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

data class ApiEndPoint(
    val ip: String,
    val port: String
) {

    private fun connectionString() = "http://$ip" + if (port.isNotBlank()) ":$port" else ""

    fun routeConnect() = connectionString() + "/connect"

    fun routeListModels() = connectionString() + "/list"

    fun routePredict() = connectionString() + "/predict"

    fun equals(ip: String, port: String): Boolean {
        return this.ip == ip && this.port == port
    }

}

val ApiEndPoint.client: HttpClient
    get() = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 3000
            socketTimeoutMillis = 5000
        }
    }
