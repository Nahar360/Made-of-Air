package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.models.domain.*
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.PostsMusicRepository
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

const val ADD_ALBUM = "/add_album"

@Location(ADD_ALBUM)
class AddAlbum

fun Route.addAlbum(
    usersRepository: UsersRepository,
    musicRepository: MusicRepository,
    postsMusicRepository: PostsMusicRepository,
    years: ArrayList<String>
) {
    get<AddAlbum> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        val months = getAllMonthsString()
        val genres = getAllGenresString().map { it.replace(" ", "_").toUpperCase() }

        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val currentMonth = calendarMonthToMonths(SimpleDateFormat("MMM").format(Calendar.getInstance().time)).name

        call.respond(
            FreeMarkerContent(
                "add_album.ftl",
                mapOf(
                    "user" to user,
                    "years" to years,
                    "months" to months,
                    "genres" to genres,
                    "currentYear" to currentYear,
                    "currentMonth" to currentMonth
                )
            )
        )
    }

    post<AddAlbum> {
        val params = call.receiveParameters()

        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }

        if (getAction(params) == Actions.ADD) {
            if (musicRepository.add(addAlbum(params)) != null &&
                postsMusicRepository.add(addMusicPost(params, user)) != null
            ) {
                call.redirect(MusicPerYear(params.getValue("year")))
            }
        }
    }
}

private fun addAlbum(params: Parameters): Music {
    var rating = ""
    if (params.contains("rating")) {
        rating = params.getValue("rating")
    }

    return Music(
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        band = params.getValue("band"),
        album = params.getValue("album"),
        genre = genreStringToGenreEnumFromDropDown(params.getValue("genre")),
        rating = rating,
        bestSong = params.getValue("bestSong")
    )
}

private fun addMusicPost(params: Parameters, author: User?): PostsMusic {
    var rating = ""
    if (params.contains("rating")) {
        rating = params.getValue("rating")
    }

    return PostsMusic(
        author = author!!.userId,
        date = DateTime.now(),
        type = PostType.ADD,
        year = yearStringToYearEnum(params.getValue("year")),
        month = monthStringToMonthEnum(params.getValue("month")),
        band = params.getValue("band"),
        album = params.getValue("album"),
        genre = genreStringToGenreEnumFromDropDown(params.getValue("genre")),
        rating = rating,
        bestSong = params.getValue("bestSong")
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: Music $value")
}
