package com.madeofair.models.domain

import com.madeofair.auth.hash
import io.ktor.auth.Principal
import java.io.Serializable

private const val DEFAULT_USER_ID = "admin"
private const val DEFAULT_USER_PASSWORD = "madeofair2020"

data class User(
    val userId: String,
    val hashPassword: String
) : Serializable, Principal

fun defaultUser() = User(
    userId = DEFAULT_USER_ID,
    hashPassword = hash(DEFAULT_USER_PASSWORD)
)
