package com.madeofair.models.db

import org.jetbrains.exposed.sql.Table

object MusicDB : Table() {
    val id = varchar("id", 10).primaryKey()
    val year = varchar("year", 20)
    val month = varchar("month", 20)
    val band = varchar("band", 100)
    val album = varchar("album", 100)
    val genre = varchar("genre", 50)
    val rating = varchar("rating", 5)
    val bestSong = varchar("bestSong", 100)
}
