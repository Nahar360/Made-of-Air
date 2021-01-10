package com.madeofair.models.db

import org.jetbrains.exposed.sql.Table

object ArticlesDB : Table() {
    val id = varchar("id", 10).primaryKey()
    val year = varchar("year", 20)
    val month = varchar("month", 20)
    val title = varchar("title", 100)
    val img = varchar("img", 200)
    val href = varchar("href", 100)
}
