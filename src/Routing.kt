package com.madeofair

import com.madeofair.models.domain.getAllYearsString
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.PitchforkRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.pages.*
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.static() {
    static("/static") {
    }
}

fun Routing.web(
    usersRepository: UsersRepository,
    musicRepository: MusicRepository,
    pitchforkRepository: PitchforkRepository
) {
    val years = getAllYearsString()

    home(usersRepository, years)

    signIn(usersRepository, years)
    users(usersRepository, years)
    signOut()

    addAlbum(usersRepository, musicRepository, years)
    musicPerYear(usersRepository, musicRepository, years)
    modifyAlbum(usersRepository, musicRepository, years)

    addAlbumPitchfork(usersRepository, pitchforkRepository, years)
    pitchforkPerYear(usersRepository, pitchforkRepository, years)
    modifyAlbumPitchfork(usersRepository, pitchforkRepository, years)

    stats(usersRepository, musicRepository, years)
    statsPitchfork(usersRepository, pitchforkRepository, years)

    about(usersRepository, years)

    loadCsv(musicRepository, pitchforkRepository)
}
