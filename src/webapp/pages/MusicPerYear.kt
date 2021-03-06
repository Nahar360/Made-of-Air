package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Music
import com.madeofair.models.domain.calendarMonthToMonths
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
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
import java.text.SimpleDateFormat
import java.util.*

const val MUSIC_PER_YEAR = "/music/year/{year}"

@Location(MUSIC_PER_YEAR)
class MusicPerYear(val year: String)

fun Route.musicPerYear(usersRepository: UsersRepository, musicRepository: MusicRepository, years: ArrayList<String>) {
    get<MusicPerYear> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val months = getAllMonthsString()

        val music = ArrayList<List<Music>>()
        val params: Parameters = call.parameters
        val year =
            params["year"] ?: return@get call.respond(status = HttpStatusCode.BadRequest, message = "Bad request")
        for (month in months)
            music.add(musicRepository.getAllByYearAndByMonth("YEAR_$year", month).sortedBy { it.band }
                .sortedBy { it.month }.sortedBy { it.year })

        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val currentMonth = calendarMonthToMonths(SimpleDateFormat("MMM").format(Calendar.getInstance().time)).name

        call.respond(
            FreeMarkerContent(
                "music.ftl",
                mapOf(
                    "user" to user,
                    "music" to music,
                    "currentYear" to currentYear,
                    "currentMonth" to currentMonth,
                    "months" to months,
                    "year" to year,
                    "years" to years
                )
            )
        )
    }

    post<MusicPerYear> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.DELETE) {
            val id = params.getValue("musicId")
            if (musicRepository.remove(id)) {
                call.redirect(MusicPerYear(it.year))
            }
        } else if (getAction(params) == Actions.CLEAR) {
            val year = it.year
            musicRepository.clearAllByYear("YEAR_$year")
            call.redirect(MusicPerYear(it.year))
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Album $value")
}
