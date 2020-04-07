package com.madeofair.repositories

import kotlin.random.Random

private const val ID_LENGTH = 8
private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun createRandomId(): String {
    return (1..ID_LENGTH)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}
