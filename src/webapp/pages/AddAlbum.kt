package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Music
import com.madeofair.models.domain.monthStringToMonthEnum
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
import java.time.Year

const val ADD_ALBUM = "/add_album"

@Location(ADD_ALBUM)
class AddAlbum

fun Route.addAlbum(usersRepository: UsersRepository, musicRepository: MusicRepository) {
    get<AddAlbum> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        call.respond(
            FreeMarkerContent(
                "add_album.ftl",
                mapOf("user" to user)
            )
        )
    }

    post<AddAlbum> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.ADD) {
            if (musicRepository.add(addAlbum(params)) != null) {
                call.redirect(MusicPerYear(params.getValue("year")))
            }
        }
    }
}

private fun addAlbum(params: Parameters): Music {
    return Music(
        year = Year.parse(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        band = params.getValue("band"),
        album = params.getValue("album"),
        genre = params.getValue("genre"),
        rating = params.getValue("rating").toInt(),
        bestSong = params.getValue("bestSong")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Music $value")
}
