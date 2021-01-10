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

const val ADD_ARTICLE = "/add_article"

@Location(ADD_ARTICLE)
class AddArticle

fun Route.addArticle(
    usersRepository: UsersRepository,
    articlesRepository: ArticlesRepository,
    years: ArrayList<String>
) {
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
