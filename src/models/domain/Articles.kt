package com.madeofair.models.domain

data class Articles(
    val id: String? = null,
    val year: Years,
    val month: Months,
    val title: String,
    val img: String,
    val href: String
)
