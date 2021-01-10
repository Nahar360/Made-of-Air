package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.repositories.UsersRepository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

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
