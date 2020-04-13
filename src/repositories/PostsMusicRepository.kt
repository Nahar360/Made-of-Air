package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.PostsMusicDB
import com.madeofair.models.domain.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object PostsMusicRepository {

    suspend fun add(postMusic: PostsMusic): PostsMusic? {
        return dbQuery {
            val insertStatement = transaction {
                PostsMusicDB.insert {
                    it[id] = createRandomId()
                    it[author] = postMusic.author
                    it[date] = postMusic.date
                    it[type] = postMusic.type.name
                    it[year] = postMusic.year.name
                    it[month] = postMusic.month.name
                    it[band] = postMusic.band
                    it[album] = postMusic.album
                    it[genre] = postMusic.genre.name
                    it[rating] = postMusic.rating
                    it[bestSong] = postMusic.bestSong
                }
            }

            insertStatement.resultedValues?.get(0)?.toPostsMusic()
        }
    }

    suspend fun get(id: String): PostsMusic? {
        return dbQuery {
            PostsMusicDB.select {
                (PostsMusicDB.id eq id)
            }.mapNotNull {
                it.toPostsMusic()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<PostsMusic> {
        return dbQuery {
            PostsMusicDB.selectAll().mapNotNull {
                it.toPostsMusic()
            }
        }
    }

    suspend fun clearAll() {
        return dbQuery {
            PostsMusicDB.deleteAll()
        }
    }

    private fun ResultRow.toPostsMusic(): PostsMusic {
        return PostsMusic(
            id = this[PostsMusicDB.id],
            author = this[PostsMusicDB.author],
            date = this[PostsMusicDB.date],
            type = PostType.valueOf(this[PostsMusicDB.type]),
            year = Years.valueOf(this[PostsMusicDB.year]),
            month = Months.valueOf(this[PostsMusicDB.month]),
            band = this[PostsMusicDB.band],
            album = this[PostsMusicDB.album],
            genre = Genres.valueOf(this[PostsMusicDB.genre]),
            rating = this[PostsMusicDB.rating],
            bestSong = this[PostsMusicDB.bestSong]
        )
    }
}
