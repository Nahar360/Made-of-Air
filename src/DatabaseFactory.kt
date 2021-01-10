package com.madeofair

import com.madeofair.Environment.JDBC_DATABASE_PASSWORD
import com.madeofair.Environment.JDBC_DATABASE_URL
import com.madeofair.models.db.*
import com.madeofair.models.domain.defaultUser
import com.madeofair.modules.RepositoryModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(repositoryModule: RepositoryModule) {
        Database.connect(hiraki())

        transaction {
            SchemaUtils.create(UserDB)
            SchemaUtils.create(MusicDB)
            SchemaUtils.create(PitchforkDB)
            SchemaUtils.create(PostsMusicDB)
            SchemaUtils.create(PostsPitchforkDB)
            SchemaUtils.create(ArticlesDB)
        }

        loadDefaultContent(repositoryModule)
    }

    private fun loadDefaultContent(repositoryModule: RepositoryModule) {
        runBlocking {
            repositoryModule.users().upsert(defaultUser())
        }
    }

    private fun hiraki(): HikariDataSource {
        val config = HikariConfig()
        config.apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = System.getenv(JDBC_DATABASE_URL)
            maximumPoolSize = 3
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            password = System.getenv(JDBC_DATABASE_PASSWORD)
            config.validate()
        }

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }
}
