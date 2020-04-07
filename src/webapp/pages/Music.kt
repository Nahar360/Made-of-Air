package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.Actions
import com.madeofair.webapp.getAction
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val MUSIC = "/music"

@Location(MUSIC)
class Music

fun Route.music(usersRepository: UsersRepository, musicRepository: MusicRepository) {
    get<Music> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }
        val music = musicRepository.getAll()

        call.respond(
            FreeMarkerContent(
                "music.ftl",
                mapOf(
                    "user" to user,
                    "music" to music
                )
            )
        )
    }

    post<Music> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.DELETE) {
            val id = params.getValue("musicId")
            if (musicRepository.remove(id)) {
                call.redirect(Music())
            }
        } else if (getAction(params) == Actions.CLEAR) {
            musicRepository.clearAll()
            call.redirect(Music())
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Patient $value")
}
