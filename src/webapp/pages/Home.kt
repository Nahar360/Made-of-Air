package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.redirect
import com.madeofair.repositories.PostsMusicRepository
import com.madeofair.repositories.PostsPitchforkRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.Actions
import com.madeofair.webapp.getAction
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val HOME = "/"

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

        val postsMusic = postsMusicRepository.getAll().sortedBy { it.date }.reversed()
        val postsPitchfork = postsPitchforkRepository.getAll().sortedBy { it.date }.reversed()

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
            postsPitchforkRepository.clearAll()
            
            call.redirect(Home())
        }
    }
}
