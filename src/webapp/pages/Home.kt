package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.repositories.UsersRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val HOME = "/"

@Location(HOME)
class Home

fun Route.home(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<Home> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "home.ftl",
                mapOf(
                    "user" to user,
                    "years" to years
                )
            )
        )
    }
}
