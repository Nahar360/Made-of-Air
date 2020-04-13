package com.madeofair.models.domain

import org.joda.time.DateTime

data class PostsPitchfork(
    val id: String? = null,
    val author: String,
    val date: DateTime,
    val type: PostType,
    val year: Years,
    val month: Months,
    val day: Int,
    val band: String,
    val album: String,
    val rating: String
)
