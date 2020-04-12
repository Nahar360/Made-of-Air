package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.PitchforkDB
import com.madeofair.models.domain.Months
import com.madeofair.models.domain.Pitchfork
import com.madeofair.models.domain.Years
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object PitchforkRepository {

    suspend fun add(pitchfork: Pitchfork): Pitchfork? {
        return dbQuery {
            val insertStatement = transaction {
                PitchforkDB.insert {
                    it[id] = createRandomId()
                    it[year] = pitchfork.year.name
                    it[month] = pitchfork.month.name
                    it[day] = pitchfork.day
                    it[band] = pitchfork.band
                    it[album] = pitchfork.album
                    it[rating] = pitchfork.rating
                }
            }

            insertStatement.resultedValues?.get(0)?.toPitchfork()
        }
    }

    suspend fun update(pitchfork: Pitchfork): Pitchfork? {
        return dbQuery {
            val updatedCount = transaction {
                PitchforkDB.update(
                    where = { PitchforkDB.id eq pitchfork.id!! }
                ) {
                    it[year] = pitchfork.year.name
                    it[month] = pitchfork.month.name
                    it[day] = pitchfork.day
                    it[band] = pitchfork.band
                    it[album] = pitchfork.album
                    it[rating] = pitchfork.rating
                }
            }
            when (updatedCount) {
                0 -> null
                else -> pitchfork
            }
        }
    }

    suspend fun get(id: String): Pitchfork? {
        return dbQuery {
            PitchforkDB.select {
                (PitchforkDB.id eq id)
            }.mapNotNull {
                it.toPitchfork()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<Pitchfork> {
        return dbQuery {
            PitchforkDB.selectAll().mapNotNull {
                it.toPitchfork()
            }
        }
    }

    suspend fun getAllByYear(year: String): List<Pitchfork> {
        return dbQuery {
            PitchforkDB.select {
                PitchforkDB.year eq year
            }.mapNotNull {
                it.toPitchfork()
            }
        }
    }

    suspend fun getAllByMonth(month: String): List<Pitchfork> {
        return dbQuery {
            PitchforkDB.select {
                PitchforkDB.month eq month
            }.mapNotNull {
                it.toPitchfork()
            }
        }
    }

    suspend fun getAllByYearAndByMonth(year: String, month: String): List<Pitchfork> {
        return dbQuery {
            PitchforkDB.select {
                (PitchforkDB.year eq year) and (PitchforkDB.month eq month)
            }.mapNotNull {
                it.toPitchfork()
            }
        }
    }

    suspend fun getAllByRatingThreshold(rating: String): List<Pitchfork> {
        return dbQuery {
            PitchforkDB.select {
                PitchforkDB.rating greaterEq rating
            }.mapNotNull {
                it.toPitchfork()
            }
        }
    }

    suspend fun remove(id: String): Boolean {
        return dbQuery {
            val count = transaction {
                PitchforkDB.deleteWhere {
                    PitchforkDB.id eq id
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
            PitchforkDB.deleteAll()
        }
    }

    suspend fun clearAllByYear(year: String): Boolean {
        return dbQuery {
            val count = transaction {
                PitchforkDB.deleteWhere {
                    PitchforkDB.year eq year
                }
            }

            when (count) {
                0 -> false
                else -> true
            }
        }
    }

    private fun ResultRow.toPitchfork(): Pitchfork {
        return Pitchfork(
            id = this[PitchforkDB.id],
            year = Years.valueOf(this[PitchforkDB.year]),
            month = Months.valueOf(this[PitchforkDB.month]),
            day = this[PitchforkDB.day],
            band = this[PitchforkDB.band],
            album = this[PitchforkDB.album],
            rating = this[PitchforkDB.rating]
        )
    }
}
