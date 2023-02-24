package com.silverbullet.feature_messages.route

import com.silverbullet.feature_messages.MessagesController
import com.silverbullet.feature_messages.request.SendMessageRequest
import com.silverbullet.utils.InvalidRequestBody
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendMessageRoute(controller: MessagesController) {

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