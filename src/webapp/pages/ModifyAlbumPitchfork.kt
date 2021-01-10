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
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.get
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.Route
import io.ktor.sessions.*

const val MODIFY_ALBUM_PITCHFORK = "pitchfork/{id}"

@Location(MODIFY_ALBUM_PITCHFORK)
class ModifyAlbumPitchfork(val id: String)

fun Route.modifyAlbumPitchfork(
    usersRepository: UsersRepository,
    pitchforkRepository: PitchforkRepository,
    years: ArrayList<String>
) {
    get<ModifyAlbumPitchfork> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        if (user == null) {
            call.redirect(SignIn("", "Please, sign in to continue."))
        } else {
            val params: Parameters = call.parameters
            val id =
                params["id"] ?: return@get call.respond(status = HttpStatusCode.BadRequest, message = "Bad request")
            val album = pitchforkRepository.get(id)

            val months = getAllMonthsString()

            if (album != null) {
                call.respond(
                    FreeMarkerContent(
                        "modify_album_pitchfork.ftl",
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

    post<ModifyAlbumPitchfork> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.MODIFY && pitchforkRepository.update(createAlbumToUpdate(params)) != null) {
            call.redirect(PitchforkPerYear(params.getValue("year")))
        }
    }
}

private fun createAlbumToUpdate(params: Parameters): Pitchfork {
    return Pitchfork(
        id = params.getValue("id"),
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        day = params.getValue("day").toInt(),
        band = params.getValue("band"),
        album = params.getValue("album"),
        rating = params.getValue("rating")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Music $value")
}
