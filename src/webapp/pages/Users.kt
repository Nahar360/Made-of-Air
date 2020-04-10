package com.madeofair.webapp.pages

import com.madeofair.auth.hash
import com.madeofair.models.UserSession
import com.madeofair.models.domain.User
import com.madeofair.redirect
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

const val MIN_USER_ID_LENGTH = 5
const val MIN_PASSWORD_LENGTH = 6

const val USERS = "users"

@Location(USERS)
data class Users(val error: String = "")

fun Route.users(usersRepository: UsersRepository, years: ArrayList<String>) {
    get<Users> {
        val user = call.sessions.get<UserSession>()?.let { usersRepository.get(it.userId) }
        val users = usersRepository.getAll()

        call.respond(
            FreeMarkerContent(
                "users.ftl",
                mapOf(
                    "error" to it.error,
                    "user" to user,
                    "users" to users,
                    "years" to years
                )
            )
        )
    }

    post<Users> {
        val params = call.receiveParameters()

        if (getAction(params) == Actions.ADD) {
            val userId = params.getValue("userId")
            val password = params.getValue("password")
            when {
                userId.length < MIN_USER_ID_LENGTH -> {
                    call.redirect(Users("Username must contain at least 5 characters."))
                }
                password.length < MIN_PASSWORD_LENGTH -> {
                    call.redirect(Users("Password must contain at least 6 characters."))
                }
                else -> {
                    val hashPassword = hash(password)
                    val userToCreate = createUser(userId, hashPassword)
                    if (usersRepository.get(userId) == null) {
                        usersRepository.add(userToCreate)
                        call.redirect(Users())
                    } else {
                        val usersError = Users()
                        call.redirect(usersError.copy(error = "User already exists."))
                    }
                }
            }
        } else if (getAction(params) == Actions.DELETE) {
            if (usersRepository.getAll().size > 1) {
                val userId = params.getValue("userId")
                usersRepository.remove(userId)
            }

            call.redirect(Users())
        }
    }
}

private fun createUser(userId: String, hashPassword: String): User {
    return User(
        userId = userId,
        hashPassword = hashPassword
    )
}

private fun Parameters.getValue(value: String): String {
    return this[value] ?: throw IllegalArgumentException("Missing argument: User $value")
}
