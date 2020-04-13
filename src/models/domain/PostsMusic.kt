package com.madeofair.models.domain

import org.joda.time.DateTime

data class PostsMusic(
    val id: String? = null,
    val author: String,
    val date: DateTime,
    val type: PostType,
    val year: Years,
    val month: Months,
    val band: String,
    val album: String,
    val genre: Genres,
    val rating: String,
    val bestSong: String
)
