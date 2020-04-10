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

const val ABOUT = "/about"

@Location(ABOUT)
class About

fun Route.about(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<About> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "about.ftl",
                mapOf(
                    "user" to user,
                    "years" to years
                )
            )
        )
    }
}
