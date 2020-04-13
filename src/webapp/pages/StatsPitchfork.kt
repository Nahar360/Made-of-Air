package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Pitchfork
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.models.domain.yearEnumToYearString
import com.madeofair.redirect
import com.madeofair.repositories.PitchforkRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.Actions
import com.madeofair.webapp.getAction
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

const val STATS_PITCHFORK = "/stats_pitchfork"

@Location(STATS_PITCHFORK)
class StatsPitchfork

fun Route.statsPitchfork(
    usersRepository: UsersRepository,
    pitchforkRepository: PitchforkRepository,
    years: ArrayList<String>
) {
    get<StatsPitchfork> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val musicByYears = ArrayList<List<Pitchfork>>()
        for (year in years)
            musicByYears.add(pitchforkRepository.getAllByYear("YEAR_$year"))

        val listOfAverageRatings = ArrayList<Double>()
        for (albumsPerYear in musicByYears) {
            var avg = 0.0
            for (album in albumsPerYear) {
                avg += album.rating.toDouble()
            }

            listOfAverageRatings.add(avg / albumsPerYear.size)
        }

        val months = getAllMonthsString()

        call.respond(
            FreeMarkerContent(
                "stats_pitchfork.ftl",
                mapOf(
                    "user" to user,
                    "musicByYears" to musicByYears,
                    "years" to years,
                    "listOfAverageRatings" to listOfAverageRatings,
                    "months" to months
                )
            )
        )
    }

    post<StatsPitchfork> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.CLEAR) {
            pitchforkRepository.clearAll()
            call.redirect(StatsPitchfork())
        } else if (getAction(params) == Actions.EXPORT) {
            val csvFile = exportToCsv(pitchforkRepository)
            if (csvFile.exists()) {
                call.response.header("Content-Disposition", "attachment; filename=\"${csvFile.name}\"")
                call.respondFile(csvFile)

                csvFile.delete()
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

suspend fun exportToCsv(pitchforkRepository: PitchforkRepository): File {
    val csvFilePath = "./pitchfork.csv"
    val writer = Files.newBufferedWriter(Paths.get(csvFilePath))

    val csvPrinter = CSVPrinter(
        writer, CSVFormat.DEFAULT
            .withHeader(
                "YEAR",
                "MONTH",
                "BAND",
                "DAY",
                "ALBUM",
                "RATING"
            )
    )

    val music = pitchforkRepository.getAll()
    csvPrinter.run {
        for (album in music) {
            printRecord(
                yearEnumToYearString(album.year),
                album.month,
                album.day,
                album.band,
                album.album,
                album.rating
            )
        }
        flush()
        close()
    }

    return File(csvFilePath);
}
