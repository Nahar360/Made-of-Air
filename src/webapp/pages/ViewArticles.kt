package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.repositories.UsersRepository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val VIEW_ARTICLES = "/view_articles"

@Location(VIEW_ARTICLES)
class View_Articles

fun Route.viewArticles(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<View_Articles> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "view_articles.ftl",
                mapOf(
                    "user" to user,
                    "years" to years
                )
            )
        )
    }
}
