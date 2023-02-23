package com.silverbullet.plugins

import com.silverbullet.core.events.eventsJsonSerializer
import io.ktor.serialization.kotlinx.*
import io.ktor.server.websocket.*
import java.time.Duration
import io.ktor.server.application.*

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(eventsJsonSerializer)
    }

}
