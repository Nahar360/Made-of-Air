package com.madeofair.models.domain

import java.time.Month

data class Music(
    val id: String? = null,
    val year: String,
    val month: String,
    val band: String,
    val album: String,
    val genre: String,
    val rating: Int,
    val bestSong: String
)
