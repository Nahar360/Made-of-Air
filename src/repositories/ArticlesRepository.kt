package com.madeofair.repositories

import com.madeofair.DatabaseFactory.dbQuery
import com.madeofair.models.db.ArticlesDB
import com.madeofair.models.domain.Articles
import com.madeofair.models.domain.Months
import com.madeofair.models.domain.Years
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ArticlesRepository {

    suspend fun add(article: Articles): Articles? {
        return dbQuery {
            val insertStatement = transaction {
                ArticlesDB.insert {
                    it[id] = createRandomId()
                    it[year] = article.year.name
                    it[month] = article.month.name
                    it[title] = article.title
                    it[img] = article.img
                    it[href] = article.href
                }
            }

            insertStatement.resultedValues?.get(0)?.toArticles()
        }
    }

    suspend fun update(article: Articles): Articles? {
        return dbQuery {
            val updatedCount = transaction {
                ArticlesDB.update(
                    where = { ArticlesDB.id eq article.id!! }
                ) {
                    it[year] = article.year.name
                    it[month] = article.month.name
                    it[title] = article.title
                    it[img] = article.img
                    it[href] = article.href
                }
            }
            when (updatedCount) {
                0 -> null
                else -> article
            }
        }
    }

    suspend fun get(id: String): Articles? {
        return dbQuery {
            ArticlesDB.select {
                (ArticlesDB.id eq id)
            }.mapNotNull {
                it.toArticles()
            }.singleOrNull()
        }
    }

    suspend fun getAll(): List<Articles> {
        return dbQuery {
            ArticlesDB.selectAll().mapNotNull {
                it.toArticles()
            }
        }
    }

    suspend fun getAllByYear(year: String): List<Articles> {
        return dbQuery {
            ArticlesDB.select {
                ArticlesDB.year eq year
            }.mapNotNull {
                it.toArticles()
            }
        }
    }

    suspend fun getAllByMonth(month: String): List<Articles> {
        return dbQuery {
            ArticlesDB.select {
                ArticlesDB.month eq month
            }.mapNotNull {
                it.toArticles()
            }
        }
    }

    suspend fun getAllByYearAndByMonth(year: String, month: String): List<Articles> {
        return dbQuery {
            ArticlesDB.select {
                (ArticlesDB.year eq year) and (ArticlesDB.month eq month)
            }.mapNotNull {
                it.toArticles()
            }
        }
    }

    suspend fun remove(id: String): Boolean {
        return dbQuery {
            val count = transaction {
                ArticlesDB.deleteWhere {
                    ArticlesDB.id eq id
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
            ArticlesDB.deleteAll()
        }
    }

    suspend fun clearAllByYear(year: String): Boolean {
        return dbQuery {
            val count = transaction {
                ArticlesDB.deleteWhere {
                    ArticlesDB.year eq year
                }
            }

            when (count) {
                0 -> false
                else -> true
            }
        }
    }

    private fun ResultRow.toArticles(): Articles {
        return Articles(
            id = this[ArticlesDB.id],
            year = Years.valueOf(this[ArticlesDB.year]),
            month = Months.valueOf(this[ArticlesDB.month]),
            title = this[ArticlesDB.title],
            img = this[ArticlesDB.img],
            href = this[ArticlesDB.href]
        )
    }
}
