package com.madeofair.models.domain

data class Music(
    val id: String? = null,
    val year: Years,
    val month: Months,
    val band: String,
    val album: String,
    val genre: Genres,
    val rating: String,
    val bestSong: String
)
