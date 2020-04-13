package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Music
import com.madeofair.models.domain.getAllGenresString
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.models.domain.yearEnumToYearString
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
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

const val STATS = "/stats"

@Location(STATS)
class Stats

fun Route.stats(usersRepository: UsersRepository, musicRepository: MusicRepository, years: ArrayList<String>) {
    get<Stats> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val musicByYears = ArrayList<List<Music>>()
        for (year in years)
            musicByYears.add(musicRepository.getAllByYear("YEAR_$year"))

        val listOfAverageRatings = ArrayList<Double>()
        for (albumsPerYear in musicByYears) {
            var avg = 0.0
            var albumWithRatingPerYear = 0
            for (album in albumsPerYear) {
                if (album.rating != "") {
                    avg += album.rating.toInt()
                    albumWithRatingPerYear++
                }
            }

            listOfAverageRatings.add(avg / albumWithRatingPerYear)
        }

        val musicByGenres = ArrayList<List<Music>>()
        val genres = getAllGenresString()
        for (genre in genres)
            musicByGenres.add(musicRepository.getAllByGenre(genre.replace(" ", "_").toUpperCase()))

        val months = getAllMonthsString()

        call.respond(
            FreeMarkerContent(
                "stats.ftl",
                mapOf(
                    "user" to user,
                    "musicByYears" to musicByYears,
                    "years" to years,
                    "listOfAverageRatings" to listOfAverageRatings,
                    "musicByGenres" to musicByGenres,
                    "genres" to genres,
                    "months" to months
                )
            )
        )
    }

    post<Stats> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.CLEAR) {
            musicRepository.clearAll()
            call.redirect(Stats())
        } else if (getAction(params) == Actions.EXPORT) {
            val csvFile = exportToCsv(musicRepository)
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

suspend fun exportToCsv(musicRepository: MusicRepository): File {
    val csvFilePath = "./music.csv"
    val writer = Files.newBufferedWriter(Paths.get(csvFilePath))

    val csvPrinter = CSVPrinter(
        writer, CSVFormat.DEFAULT
            .withHeader(
                "YEAR",
                "MONTH",
                "BAND",
                "ALBUM",
                "GENRE",
                "RATING",
                "BEST SONG"
            )
    )

    val music = musicRepository.getAll()
    csvPrinter.run {
        for (album in music) {
            printRecord(
                yearEnumToYearString(album.year),
                album.month,
                album.band,
                album.album,
                album.genre,
                album.rating,
                album.bestSong
            )
        }
        flush()
        close()
    }

    return File(csvFilePath);
}
