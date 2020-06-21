package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.*
import com.madeofair.redirect
import com.madeofair.repositories.PitchforkRepository
import com.madeofair.repositories.PostsPitchforkRepository
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
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

const val ADD_ALBUM_PITCHFORK = "/add_album_pitchfork"

@Location(ADD_ALBUM_PITCHFORK)
class AddAlbumPitchfork

fun Route.addAlbumPitchfork(
    usersRepository: UsersRepository,
    pitchforkRepository: PitchforkRepository,
    postsPitchforkRepository: PostsPitchforkRepository,
    years: ArrayList<String>
) {
    get<AddAlbumPitchfork> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val months = getAllMonthsString()

        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val currentMonth = calendarMonthToMonths(SimpleDateFormat("MMM").format(Calendar.getInstance().time)).name

        call.respond(
            FreeMarkerContent(
                "add_album_pitchfork.ftl",
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

    post<AddAlbumPitchfork> {
        val params = call.receiveParameters()

        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        if (getAction(params) == Actions.ADD) {
            if (pitchforkRepository.add(addAlbum(params)) != null &&
                postsPitchforkRepository.add(addPitchforkPost(params, user)) != null) {
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
        band = params.getValue("band").toUpperCase(),
        album = params.getValue("album").toUpperCase(),
        rating = params.getValue("rating")
    )
}

private fun addPitchforkPost(params: Parameters, author: User?): PostsPitchfork {
    return PostsPitchfork(
        author = author!!.userId,
        date = DateTime.now(),
        type = PostType.ADD,
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        day = params.getValue("day").toInt(),
        band = params.getValue("band").toUpperCase(),
        album = params.getValue("album").toUpperCase(),
        rating = params.getValue("rating")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Pitchfork $value")
}
