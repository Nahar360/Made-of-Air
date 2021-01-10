package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.redirect
import com.madeofair.repositories.PostsMusicRepository
import com.madeofair.repositories.PostsPitchforkRepository
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

const val HOME = "/"
const val MAX_NUM_ENTRIES = 25

@Location(HOME)
class Home

fun Route.home(
    usersRepository: UsersRepository,
    postsMusicRepository: PostsMusicRepository,
    postsPitchforkRepository: PostsPitchforkRepository,
    years: ArrayList<String>
) {
    get<Home> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val postsMusic = postsMusicRepository.getAll().sortedBy { it.date }.reversed().take(MAX_NUM_ENTRIES)
        val postsPitchfork = postsPitchforkRepository.getAll().sortedBy { it.date }.reversed().take(MAX_NUM_ENTRIES)

        call.respond(
            FreeMarkerContent(
                "home.ftl",
                mapOf(
                    "user" to user,
                    "years" to years,
                    "postsMusic" to postsMusic,
                    "postsPitchfork" to postsPitchfork
                )
            )
        )
    }

    post<Home> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.CLEAR) {
            postsMusicRepository.clearAll()
            // postsPitchforkRepository.clearAll()

            call.redirect(Home())
        } else if (getAction(params) == Actions.DELETE) {
            val id = params.getValue("postId")
            if (postsMusicRepository.remove(id)) {
                call.redirect(Home())
            }
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Album $value")
}
