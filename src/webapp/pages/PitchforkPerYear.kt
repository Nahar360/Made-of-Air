package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Pitchfork
import com.madeofair.models.domain.calendarMonthToMonths
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.redirect
import com.madeofair.repositories.PitchforkRepository
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val PITCHFORK_PER_YEAR = "/pitchfork/year/{year}"

@Location(PITCHFORK_PER_YEAR)
class PitchforkPerYear(val year: String)

fun Route.pitchforkPerYear(
    usersRepository: UsersRepository,
    pitchforkRepository: PitchforkRepository,
    years: ArrayList<String>
) {
    get<PitchforkPerYear> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val months = getAllMonthsString()

        val pitchfork = ArrayList<List<Pitchfork>>()
        val params: Parameters = call.parameters
        val year =
            params["year"] ?: return@get call.respond(status = HttpStatusCode.BadRequest, message = "Bad request")
        for (month in months)
            pitchfork.add(pitchforkRepository.getAllByYearAndByMonth("YEAR_$year", month).sortedBy { it.day })

        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val currentMonth = calendarMonthToMonths(SimpleDateFormat("MMM").format(Calendar.getInstance().time)).name

        call.respond(
            FreeMarkerContent(
                "pitchfork.ftl",
                mapOf(
                    "user" to user,
                    "pitchfork" to pitchfork,
                    "currentYear" to currentYear,
                    "currentMonth" to currentMonth,
                    "months" to months,
                    "year" to year,
                    "years" to years
                )
            )
        )
    }

    post<PitchforkPerYear> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.DELETE) {
            val id = params.getValue("pitchforkId")
            if (pitchforkRepository.remove(id)) {
                call.redirect(PitchforkPerYear(it.year))
            }
        } else if (getAction(params) == Actions.CLEAR) {
            val year = it.year
            pitchforkRepository.clearAllByYear("YEAR_$year")
            call.redirect(PitchforkPerYear(it.year))
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Pitchfork $value")
}
