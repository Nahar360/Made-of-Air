package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Pitchfork
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.models.domain.monthStringToMonthEnum
import com.madeofair.models.domain.yearStringToYearEnum
import com.madeofair.redirect
import com.madeofair.repositories.PitchforkRepository
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

const val ADD_ALBUM_PITCHFORK = "/add_album_pitchfork"

@Location(ADD_ALBUM_PITCHFORK)
class AddAlbumPitchfork

fun Route.addAlbumPitchfork(
    usersRepository: UsersRepository,
    pitchforkRepository: PitchforkRepository,
    years: ArrayList<String>
) {
    get<AddAlbumPitchfork> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val months = getAllMonthsString()

        call.respond(
            FreeMarkerContent(
                "add_album_pitchfork.ftl",
                mapOf(
                    "user" to user,
                    "years" to years,
                    "months" to months
                )
            )
        )
    }

    post<AddAlbumPitchfork> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.ADD) {
            if (pitchforkRepository.add(addAlbum(params)) != null) {
                call.redirect(PitchforkPerYear(params.getValue("year")))
            }
        }
    }
}

private fun addAlbum(params: Parameters): Pitchfork {
    return Pitchfork(
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        day = params.getValue("day").toInt(),
        band = params.getValue("band"),
        album = params.getValue("album"),
        rating = params.getValue("rating")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Pitchfork $value")
}
