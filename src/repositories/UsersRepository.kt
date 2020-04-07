package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.UserDB
import com.madeofair.models.domain.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UsersRepository {
    suspend fun add(list: List<User>): List<User> {
        return list.mapNotNull {
            add(it)
        }
    }

    suspend fun add(user: User): User? {
        return dbQuery {
            val insertStatement = transaction {
                UserDB.insert {
                    it[userId] = user.userId
                    it[password] = user.hashPassword
                }
            }
            insertStatement.resultedValues?.get(0)?.toUser()
        }
    }

    suspend fun upsert(user: User): User? {
        return dbQuery {
            val insertStatement = transaction {
                UserDB.insertIgnore {
                    it[userId] = user.userId
                    it[password] = user.hashPassword
                }
            }
            insertStatement.resultedValues?.get(0)?.toUser()
        }
    }

    suspend fun get(userId: String): User? {
        return dbQuery {
            UserDB.select {
                (UserDB.userId eq userId)
            }.mapNotNull {
                it.toUser()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<User> {
        return dbQuery {
            UserDB.selectAll().mapNotNull {
                it.toUser()
            }
        }
    }

    suspend fun getUserWithHash(userId: String, hash: String): User? {
        val user: User? = dbQuery {
            UserDB.select {
                (UserDB.userId eq userId)
            }.mapNotNull {
                it.toUser()
            }.singleOrNull()
        }

        return when (user?.hashPassword) {
            hash -> user
            else -> null
        }
    }

    suspend fun remove(userId: String): Boolean {
        return dbQuery {
            val count = transaction {
                UserDB.deleteWhere {
                    UserDB.userId eq userId
                }
            }

            when (count) {
                0 -> false
                else -> true
            }
        }
    }

    private fun ResultRow.toUser(): User {
        return User(
            userId = this[UserDB.userId],
            hashPassword = this[UserDB.password]
        )
    }
}
