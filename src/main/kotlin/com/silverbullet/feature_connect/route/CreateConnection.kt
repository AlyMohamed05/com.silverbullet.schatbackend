package com.silverbullet.feature_connect.route

import com.silverbullet.feature_connect.ConnectionsController
import com.silverbullet.feature_connect.request.ConnectionRequest
import com.silverbullet.utils.InvalidRequestBody
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createConnectionRoute(controller: ConnectionsController) {

    authenticate {

        post("create") {

            kotlin.runCatching {
                call.receive<ConnectionRequest>()
            }.apply {
                onSuccess { request ->
                    val userId = call.userId ?: throw UnexpectedError() // userId can't be null in authenticated req
                    controller.connectUsers(userId, request.username)
                    call.respond("connected")
                }
                onFailure {
                    throw InvalidRequestBody()
                }
            }
        }
    }
}