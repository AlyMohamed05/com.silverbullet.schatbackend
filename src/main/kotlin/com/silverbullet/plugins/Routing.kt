package com.silverbullet.plugins

import com.silverbullet.feature_auth.route.configureAuthenticationRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        configureAuthenticationRoutes()
    }
}
