package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.repositories.ArticlesRepository
import com.madeofair.repositories.UsersRepository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val ARTICLES = "/articles"

@Location(ARTICLES)
class Articles

fun Route.articles(usersRepository: UsersRepository, articlesRepository: ArticlesRepository, years: ArrayList<String>) {
    get<Articles> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "articles.ftl",
                mapOf(
                    "user" to user,
                    "years" to years
                )
            )
        )
    }
}
