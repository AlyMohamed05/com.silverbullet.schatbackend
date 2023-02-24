package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_auth.request.RefreshTokenRequest
import com.silverbullet.utils.InvalidRequestBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.refreshTokenRoute(controller: AuthenticationController) {

    post("refresh") {
        kotlin.runCatching {
            call.receive<RefreshTokenRequest>()
        }.apply {
            onSuccess { request ->
                val newAccessToken = controller.refreshToken(request.refreshToken)
                call.respond(mapOf("token" to newAccessToken))
            }
            onFailure {
                throw InvalidRequestBody()
            }
        }
    }
}