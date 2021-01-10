package com.madeofair.webapp.pages

import com.madeofair.models.UserSession
import com.madeofair.redirect
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val SIGN_OUT = "/signout"

@Location(SIGN_OUT)
class SignOut

fun Route.signOut() {
    get<SignOut> {
        call.sessions.clear<UserSession>()
        call.redirect(SignIn())
    }
}
