package com.madeofair

import com.madeofair.models.domain.getAllYearsString
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository
import com.madeofair.webapp.pages.*
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.static() {
    static("/static") {
        resources("images")
    }
}

fun Routing.web(
    usersRepository: UsersRepository,
    musicRepository: MusicRepository
) {
    val years = getAllYearsString()

    home(usersRepository, years)

    signIn(usersRepository, years)
    users(usersRepository, years)
    signOut()

    addAlbum(usersRepository, musicRepository, years)
    musicPerYear(usersRepository, musicRepository, years)
    modifyAlbum(usersRepository, musicRepository, years)

    stats(usersRepository, musicRepository, years)

    about(usersRepository, years)

    loadCsv(musicRepository)
}
