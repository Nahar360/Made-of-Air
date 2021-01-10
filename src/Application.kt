package com.madeofair

import com.madeofair.auth.COOKIES_SESSION
import com.madeofair.auth.hashKey
import com.madeofair.models.UserSession
import com.madeofair.modules.ProductionRepositoryModule
import com.madeofair.modules.RepositoryModule
import com.ryanharter.ktor.moshi.moshi
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(
    testing: Boolean = false,
    repositoryModule: RepositoryModule = ProductionRepositoryModule
) {
    setFeatures()
    initDatabase(repositoryModule, testing)
    setRouting(repositoryModule)
}

private fun Application.setFeatures() {
    install(DefaultHeaders)
    install(CallLogging)
    install(StatusPages) {
        exception<Throwable> { exception ->
            call.respondText(
                exception.localizedMessage,
                ContentType.Text.Plain,
                HttpStatusCode.InternalServerError
            )
        }
    }
    install(ContentNegotiation) {
        moshi()
        jackson()
    }
    install(Locations)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "web")
    }

    install(Sessions) {
        cookie<UserSession>(COOKIES_SESSION) {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
}

private fun initDatabase(repositoryModule: RepositoryModule, testing: Boolean) {
    if (!testing) {
        DatabaseFactory.init(repositoryModule)
    }
}

private fun Application.setRouting(repositoryModule: RepositoryModule) {
    routing {
        static()
        web(
            repositoryModule.users(),
            repositoryModule.music(),
            repositoryModule.pitchfork(),
            repositoryModule.postsMusic(),
            repositoryModule.postsPitchfork(),
            repositoryModule.articles()
        )
    }
}
