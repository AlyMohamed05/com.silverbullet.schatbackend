package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_auth.model.LoginRequest
import com.silverbullet.utils.InvalidRequestBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loginRoute(controller: AuthenticationController){
    post("login") {
        kotlin.runCatching {
            call.receive<LoginRequest>()
        }.apply {
            onSuccess { request ->
                val tokens = controller.login(request)
                call.respond(tokens)
            }
            onFailure {
                throw InvalidRequestBody()
            }
        }
    }
}