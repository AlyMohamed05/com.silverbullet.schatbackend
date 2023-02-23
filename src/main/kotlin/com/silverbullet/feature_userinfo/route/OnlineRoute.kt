package com.silverbullet.feature_userinfo.route

import com.silverbullet.feature_auth.InvalidUsername
import com.silverbullet.feature_userinfo.UserInfoController
import com.silverbullet.utils.InvalidRequestBody
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.onlineRoute(controller: UserInfoController) {

    authenticate {

        get("is-online/{username}") {

            val username = call.parameters["username"] ?: throw InvalidRequestBody()
            if (username.isBlank())
                throw InvalidUsername()

            val isOnline = controller.checkOnlineStatus(username)
            call.respond(mapOf("isOnline" to isOnline))
        }
    }
}