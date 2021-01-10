package com.madeofair

import com.madeofair.models.domain.getAllYearsString
import com.madeofair.repositories.*
import com.madeofair.webapp.pages.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Routing.static() {
    static("/static") {
    }
}

fun Routing.web(
    usersRepository: UsersRepository,
    musicRepository: MusicRepository,
    pitchforkRepository: PitchforkRepository,
    postsMusicRepository: PostsMusicRepository,
    postsPitchforkRepository: PostsPitchforkRepository
) {
    val years = getAllYearsString()

    home(usersRepository, postsMusicRepository, postsPitchforkRepository, years)

    signIn(usersRepository, years)
    users(usersRepository, years)
    signOut()

    addAlbum(usersRepository, musicRepository, postsMusicRepository, years)
    musicPerYear(usersRepository, musicRepository, years)
    modifyAlbum(usersRepository, musicRepository, postsMusicRepository, years)

    addAlbumPitchfork(usersRepository, pitchforkRepository, postsPitchforkRepository, years)
    pitchforkPerYear(usersRepository, pitchforkRepository, years)
    modifyAlbumPitchfork(usersRepository, pitchforkRepository, years)

    stats(usersRepository, musicRepository, years)
    statsPitchfork(usersRepository, pitchforkRepository, years)

    addArticle(usersRepository, years)
    articles(usersRepository, years)

    about(usersRepository, years)

    loadCsv(musicRepository, pitchforkRepository)
}
