package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.redirect
import com.madeofair.repositories.ArticlesRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.Actions
import com.madeofair.webapp.getAction
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val ARTICLES = "/articles"

@Location(ARTICLES)
class Articles

fun Route.articles(usersRepository: UsersRepository, articlesRepository: ArticlesRepository, years: ArrayList<String>) {
    get<Articles> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val articles = articlesRepository.getAll()

        call.respond(
            FreeMarkerContent(
                "articles.ftl",
                mapOf(
                    "user" to user,
                    "years" to years,
                    "articles" to articles
                )
            )
        )
    }

    post<Articles> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.DELETE) {
            val id = params.getValue("articleId")
            if (articlesRepository.remove(id)) {
                call.redirect(Articles())
            }
        } else if (getAction(params) == Actions.CLEAR) {
            articlesRepository.clearAll()
            call.redirect(Articles())
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Pitchfork $value")
}
