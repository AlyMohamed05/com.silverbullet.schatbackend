package com.silverbullet.feature_messages.route

import com.silverbullet.feature_channels.NoChannelIdFound
import com.silverbullet.feature_messages.MessagesController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.channelMessagesRoute(controller: MessagesController) {

    authenticate {

        get("{channelId}") {

            val channelId = call.parameters["channelId"]?.toIntOrNull() ?: throw NoChannelIdFound()
            val messages = controller.getChannelMessages(channelId)
            call.respond(messages)
        }
    }
}