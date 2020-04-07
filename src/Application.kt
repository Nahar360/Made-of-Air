package com.madeofair

import com.madeofair.auth.COOKIES_SESSION
import com.madeofair.auth.hashKey
import com.madeofair.models.UserSession
import com.madeofair.modules.ProductionRepositoryModule
import com.madeofair.modules.RepositoryModule
import com.ryanharter.ktor.moshi.moshi
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie

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
            repositoryModule.music()
        )
    }
}
