package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.logoutRoute(controller: AuthenticationController){

    authenticate {
        post("logout") {

            val userId = call.userId ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "invalid request")
                )
                return@post
            }
            controller.logout(userId)
            call.respond(status = HttpStatusCode.OK, message = "")
        }
    }
}