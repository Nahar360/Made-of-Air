package com.madeofair.models.domain

data class Pitchfork(
    val id: String? = null,
    val year: Years,
    val month: Months,
    val day: Int,
    val band: String,
    val album: String,
    val rating: String
)
