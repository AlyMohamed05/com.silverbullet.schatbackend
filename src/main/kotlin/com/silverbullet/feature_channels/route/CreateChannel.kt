package com.silverbullet.feature_channels.route

import com.silverbullet.feature_channels.ChannelsController
import com.silverbullet.feature_channels.model.CreateChannelRequest
import com.silverbullet.utils.InvalidRequestBody
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createChannelRoute(controller: ChannelsController) {

    authenticate {

        post("create") {

            kotlin.runCatching {
                call.receive<CreateChannelRequest>()
            }.apply {
                onSuccess { request ->
                    val userId = call.userId ?: throw UnexpectedError() // userId can't be null in authenticated req
                    controller.createChannel(userId = userId, username = request.username)
                    call.respond("created")
                }
                onFailure {
                    throw InvalidRequestBody()
                }
            }
        }
    }
}