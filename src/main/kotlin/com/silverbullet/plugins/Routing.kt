package com.silverbullet.plugins

import com.silverbullet.core.events.configureEventsDispatchingRoute
import com.silverbullet.feature_auth.route.configureAuthenticationRoutes
import com.silverbullet.feature_channels.route.configureChannelsRoutes
import com.silverbullet.feature_messages.route.configureMessagesRoutes
import com.silverbullet.feature_userinfo.route.configureUserInfoRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        configureAuthenticationRoutes()
        configureChannelsRoutes()
        configureMessagesRoutes()
        configureEventsDispatchingRoute()
        configureUserInfoRoute()
    }
}
