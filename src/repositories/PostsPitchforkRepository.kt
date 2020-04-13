package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.PostsMusicDB
import com.madeofair.models.db.PostsPitchforkDB
import com.madeofair.models.domain.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object PostsPitchforkRepository {

    suspend fun add(postPitchfork: PostsPitchfork): PostsPitchfork? {
        return dbQuery {
            val insertStatement = transaction {
                PostsPitchforkDB.insert {
                    it[id] = createRandomId()
                    it[author] = postPitchfork.author
                    it[date] = postPitchfork.date
                    it[type] = postPitchfork.type.name
                    it[year] = postPitchfork.year.name
                    it[month] = postPitchfork.month.name
                    it[day] = postPitchfork.day
                    it[band] = postPitchfork.band
                    it[album] = postPitchfork.album
                    it[rating] = postPitchfork.rating
                }
            }

            insertStatement.resultedValues?.get(0)?.toPostsPitchfork()
        }
    }

    suspend fun get(id: String): PostsPitchfork? {
        return dbQuery {
            PostsPitchforkDB.select {
                (PostsPitchforkDB.id eq id)
            }.mapNotNull {
                it.toPostsPitchfork()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<PostsPitchfork> {
        return dbQuery {
            PostsPitchforkDB.selectAll().mapNotNull {
                it.toPostsPitchfork()
            }
        }
    }

    suspend fun clearAll() {
        return dbQuery {
            PostsPitchforkDB.deleteAll()
        }
    }

    private fun ResultRow.toPostsPitchfork(): PostsPitchfork {
        return PostsPitchfork(
            id = this[PostsPitchforkDB.id],
            author = this[PostsPitchforkDB.author],
            date = this[PostsPitchforkDB.date],
            type = PostType.valueOf(this[PostsPitchforkDB.type]),
            year = Years.valueOf(this[PostsPitchforkDB.year]),
            month = Months.valueOf(this[PostsPitchforkDB.month]),
            day = this[PostsPitchforkDB.day],
            band = this[PostsPitchforkDB.band],
            album = this[PostsPitchforkDB.album],
            rating = this[PostsPitchforkDB.rating]
        )
    }
}
