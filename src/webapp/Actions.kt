package com.madeofair.webapp

import io.ktor.http.*

const val ACTION = "action"

enum class Actions {
    ADD, DELETE, CLEAR, MODIFY, EXPORT, DUPLICATE, UNKNOWN
}

fun getAction(params: Parameters): Actions {
    val action = params[ACTION] ?: throw IllegalArgumentException("Missing action")
    return Actions.values().firstOrNull { it.name.equals(action, true) } ?: Actions.UNKNOWN
}
