@file:JvmName("HasteUtils")

/**
 * Copyright © NSG, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created for NSG-API, 23:27 08.08.2021
 */
package net.neverstopgaming.haste.util

import com.google.gson.Gson
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val httpClient: HttpClient = HttpClient.newHttpClient()

var gson = Gson()
val IP_REGEX = "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(:[0-9]{0,5})".toRegex()
fun String.haste(hasteServer: String = "https://haste.neverstopgaming.net"): HasteResult {
    val request =
        HttpRequest.newBuilder(URI("$hasteServer/documents")).POST(HttpRequest.BodyPublishers.ofString(this.replace(IP_REGEX, "IP"))).build()
    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    val key = gson.fromJson<HasteResponse>(response.body(), HasteResponse::class.java)?.key
        ?: throw IllegalStateException("Cannot parse Key from Haste Result")
    return HasteResult(URL("$hasteServer/$key"), URL("$hasteServer/raw/$key"), key)
}

fun InputStream.haste(hasteServer: String = "https://hastebin.com"): HasteResult =
    this.bufferedReader().readText().haste(hasteServer)

class HasteResponse(val key: String)

data class HasteResult(val url: URL, val rawUrl: URL, val key: String)