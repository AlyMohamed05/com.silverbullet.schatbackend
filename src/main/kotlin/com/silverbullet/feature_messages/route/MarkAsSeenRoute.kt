package com.silverbullet.feature_messages.route

import com.silverbullet.feature_messages.MessagesController
import com.silverbullet.feature_messages.model.MarkAsSeenRequest
import com.silverbullet.utils.InvalidRequestBody
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.markAsSeenRoute(controller: MessagesController) {

    authenticate {

        post("mark-as-seen") {
            kotlin.runCatching {
                call.receive<MarkAsSeenRequest>()
            }.apply {
                onSuccess { request ->
                    val userId = call.userId ?: throw UnexpectedError() // userId can't be null in authenticated req.
                    // try to toggle the seen status
                    controller.updateMessageSeenStatus(
                        receiverId = userId,
                        messageId = request.messageId
                    )
                    call.respond("")
                }
                onFailure {
                    throw InvalidRequestBody()
                }
            }
        }
    }
}