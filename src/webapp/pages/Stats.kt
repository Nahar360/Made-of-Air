package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.Music
import com.madeofair.models.domain.getAllGenresString
import com.madeofair.models.domain.getAllMonthsString
import com.madeofair.models.domain.getAllYearsString
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

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
        for (albumsPerYear in musicByYears)
        {
            var avg = 0.0
            var albumWithRatingPerYear = 0
            for (album in albumsPerYear)
            {
                if (album.rating != "")
                {
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

        call.respond(
            FreeMarkerContent(
                "stats.ftl",
                mapOf(
                    "user" to user,
                    "musicByYears" to musicByYears,
                    "years" to years,
                    "listOfAverageRatings" to listOfAverageRatings,
                    "musicByGenres" to musicByGenres,
                    "genres" to genres
                )
            )
        )
    }
}
