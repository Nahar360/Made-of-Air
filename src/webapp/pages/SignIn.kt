package com.madeofair.webapp.pages

import com.madeofair.auth.hash
import com.madeofair.models.UserSession
import com.madeofair.redirect
import com.madeofair.repositories.UsersRepository
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
import io.ktor.sessions.set

const val SIGN_IN = "signin"

@Location(SIGN_IN)
data class SignIn(val userId: String = "", val error: String = "")

fun Route.signIn(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<SignIn> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }
        call.respond(
            FreeMarkerContent(
                "signin.ftl",
                mapOf(
                    "user" to user,
                    "error" to it.error,
                    "years" to years
                )
            )
        )
    }

    post<SignIn> {
        val params = call.receiveParameters()
        val userId = params.getValue("userId")
        val password = params.getValue("password")

        val hashPass: String = hash(password)
        val user = usersRepository.getUserWithHash(userId, hashPass)

        if (user == null) {
            val signInError = SignIn(userId)
            call.redirect(signInError.copy(error = "Username or password are incorrect."))
        } else {
            call.sessions.set(UserSession(user.userId))
            call.redirect(Home())
        }
    }
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: User $value")
}
