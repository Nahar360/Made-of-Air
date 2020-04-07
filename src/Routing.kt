package com.madeofair

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
    home(usersRepository)

    signIn(usersRepository)
    users(usersRepository)
    signOut()

    addAlbum(usersRepository, musicRepository)
    music(usersRepository, musicRepository)
    modifyAlbum(usersRepository, musicRepository)

    about(usersRepository)
}
