package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_auth.InvalidUsername
import com.silverbullet.utils.InvalidRequestBody
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Used if client want to validate the username before he sends a request to signup.
 */
fun Route.validateUsernameRoute(controller: AuthenticationController){

    get("is-valid/{username}") {

        val username = call.parameters["username"] ?: throw InvalidRequestBody()
        if(username.isBlank())
            throw InvalidUsername()
        controller.validateUsername(username)
        call.respond("valid")
    }
}