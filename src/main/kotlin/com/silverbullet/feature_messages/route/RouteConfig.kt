package com.silverbullet.feature_messages.route

import com.silverbullet.feature_messages.MessagesController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureMessagesRoutes() {

    val messagesController by inject<MessagesController>()

    route("messages") {

        channelMessagesRoute(controller = messagesController)
    }
}