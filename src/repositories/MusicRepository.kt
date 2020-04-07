package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.MusicDB
import com.madeofair.models.domain.Music
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object MusicRepository {

    suspend fun add(music: Music): Music? {
        return dbQuery {
            val insertStatement = transaction {
                MusicDB.insert {
                    it[id] = createRandomId()
                    it[year] = music.year
                    it[month] = music.month
                    it[band] = music.band
                    it[album] = music.album
                    it[genre] = music.genre
                    it[rating] = music.rating
                    it[bestSong] = music.bestSong
                }
            }

            insertStatement.resultedValues?.get(0)?.toMusic()
        }
    }

    suspend fun update(music: Music): Music? {
        return dbQuery {
            val updatedCount = transaction {
                MusicDB.update(
                    where = { MusicDB.id eq music.id!! }
                ) {
                    it[year] = music.year
                    it[month] = music.month
                    it[band] = music.band
                    it[album] = music.album
                    it[genre] = music.genre
                    it[rating] = music.rating
                    it[bestSong] = music.bestSong
                }
            }
            when (updatedCount) {
                0 -> null
                else -> music
            }
        }
    }

    suspend fun get(id: String): Music? {
        return dbQuery {
            MusicDB.select {
                (MusicDB.id eq id)
            }.mapNotNull {
                it.toMusic()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<Music> {
        return dbQuery {
            MusicDB.selectAll().mapNotNull {
                it.toMusic()
            }
        }
    }

    suspend fun remove(id: String): Boolean {
        return dbQuery {
            val count = transaction {
                MusicDB.deleteWhere {
                    MusicDB.id eq id
                }
            }

            when (count) {
                0 -> false
                else -> true
            }
        }
    }

    suspend fun clearAll() {
        return dbQuery {
            MusicDB.deleteAll()
        }
    }

    private fun ResultRow.toMusic(): Music {
        return Music(
            id = this[MusicDB.id],
            year = this[MusicDB.year],
            month = this[MusicDB.month],
            band = this[MusicDB.band],
            album = this[MusicDB.album],
            genre = this[MusicDB.genre],
            rating = this[MusicDB.rating],
            bestSong = this[MusicDB.bestSong]
        )
    }
}
