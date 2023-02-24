package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_auth.request.SignupRequest
import com.silverbullet.utils.InvalidRequestBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signupRoute(controller: AuthenticationController) {

    post("signup") {

        kotlin.runCatching {
            call.receive<SignupRequest>()
        }.apply {
            onSuccess { request ->
                val newUserInfo = controller.signup(request)
                call.respond(newUserInfo)
            }
            onFailure {
                throw InvalidRequestBody()
            }
        }
    }
}