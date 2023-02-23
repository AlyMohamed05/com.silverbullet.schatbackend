package com.silverbullet.core.events

import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.configureEventsDispatchingRoute() {

    val eventsDispatcher by inject<EventsDispatcher>()

    authenticate {

        webSocket("events") {
            val userId = call.userId ?: throw UnexpectedError() // userId can't be null in authenticated request.
            eventsDispatcher.subscribeUser(userId = userId, session = this)
            startPingPong(
                onPingFailed = { eventsDispatcher.unSubscribeUser(userId) }
            )
        }
    }
}