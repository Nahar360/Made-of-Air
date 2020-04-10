package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Music
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.models.domain.monthStringToMonthEnum
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.Actions
import com.madeofair.webapp.getAction
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
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

const val MODIFY_ALBUM = "music/{id}"

@Location(MODIFY_ALBUM)
class ModifyAlbum(val id: String)

fun Route.modifyAlbum(
    usersRepository: UsersRepository,
    musicRepository: MusicRepository
) {
    get<ModifyAlbum> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        if (user == null) {
            call.redirect(SignIn("", "Please, sign in to continue."))
        } else {
            val params: Parameters = call.parameters
            val id =
                params["id"] ?: return@get call.respond(status = HttpStatusCode.BadRequest, message = "Bad request")
            val album = musicRepository.get(id)

            val years = listOf("2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020")
            val months = getAllMonthsString()

            when (album != null) {
                false -> call.redirect(MusicPerYear("2016"))
                true -> {
                    call.respond(
                        FreeMarkerContent(
                            "modify_album.ftl",
                            mapOf(
                                "user" to user,
                                "album" to album,
                                "years" to years,
                                "months" to months
                            )
                        )
                    )
                }
            }
        }
    }

    post<ModifyAlbum> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.MODIFY && musicRepository.update(createAlbumToUpdate(params)) != null) {
            call.redirect(MusicPerYear(params.getValue("year")))
        }
    }
}

private fun createAlbumToUpdate(params: Parameters): Music {
    return Music(
        id = params.getValue("id"),
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
