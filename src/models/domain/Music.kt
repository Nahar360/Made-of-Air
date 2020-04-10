package com.madeofair.models.domain

import java.time.Year

data class Music(
    val id: String? = null,
    val year: Year,
    val month: Months,
    val band: String,
    val album: String,
    val genre: String,
    val rating: Int,
    val bestSong: String
)
