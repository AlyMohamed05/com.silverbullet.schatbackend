package com.silverbullet.feature_channels.route

import com.silverbullet.feature_channels.ChannelsController
import com.silverbullet.feature_channels.model.SendMessageRequest
import com.silverbullet.utils.InvalidRequestBody
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendMessageRoute(controller: ChannelsController) {

    authenticate {

        post("send") {

            kotlin.runCatching {
                call.receive<SendMessageRequest>()
            }.apply {
                onSuccess { request: SendMessageRequest ->
                    val userId = call.userId ?: throw UnexpectedError() // userId can't be null in authenticated req
                    controller.sendMessage(
                        senderId = userId,
                        request = request
                    )
                    call.respond(message = "")
                }
                onFailure {
                    throw InvalidRequestBody()
                }
            }
        }
    }
}