package com.madeofair.models.db

import org.jetbrains.exposed.sql.Table

object UserDB : Table() {
    val userId = varchar("userId", 50).primaryKey()
    val password = varchar("password", 64)
}
