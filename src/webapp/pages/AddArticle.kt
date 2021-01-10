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

const val ADD_ARTICLE = "/add_article"

@Location(ADD_ARTICLE)
class AddArticle

fun Route.addArticle(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<AddArticle> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "add_article.ftl",
                mapOf(
                    "user" to user,
                    "years" to years
                )
            )
        )
    }
}
