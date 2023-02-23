package com.silverbullet.plugins

import com.silverbullet.utils.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPages() {

    install(StatusPages) {

        exception<ApplicationException> { call, cause ->
            cause.handle(call)
        }
    }
}