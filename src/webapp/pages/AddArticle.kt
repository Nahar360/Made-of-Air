package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.*
import com.madeofair.models.domain.Articles
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

        val months = getAllMonthsString()

        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val currentMonth = calendarMonthToMonths(SimpleDateFormat("MMM").format(Calendar.getInstance().time)).name

        call.respond(
            FreeMarkerContent(
                "add_article.ftl",
                mapOf(
                    "user" to user,
                    "years" to years,
                    "months" to months,
                    "currentYear" to currentYear,
                    "currentMonth" to currentMonth
                )
            )
        )
    }

    post<AddArticle> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.ADD) {
            if (articlesRepository.add(addArticle(params)) != null) {
                call.redirect(Articles())
            }
        }
    }
}

private fun addArticle(params: Parameters): Articles {
    return Articles(
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        title = params.getValue("title"),
        img = params.getValue("img"),
        href = params.getValue("href")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Articles $value")
}
