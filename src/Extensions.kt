package com.madeofair

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*

suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}
