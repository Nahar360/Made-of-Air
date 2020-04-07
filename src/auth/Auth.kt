package com.madeofair.auth

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpHeaders
import io.ktor.request.header
import io.ktor.util.hex
import java.net.URI
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val COOKIES_SESSION = "COOKIES_SESSION"

val hashKey = hex(System.getenv("HASH_SECRET_KEY"))
val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)

    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let {
    URI.create(it).host
}
