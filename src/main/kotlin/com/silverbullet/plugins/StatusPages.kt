package com.silverbullet.plugins

import com.silverbullet.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {

    install(StatusPages) {

        exception<ApplicationException> { call, cause ->
            when (cause) {

                is InvalidRequestBody -> call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to cause.error)
                )

                is UsernameAlreadyExists -> call.respond(
                    status = HttpStatusCode.Conflict,
                    message = mapOf("error" to cause.error)
                )

                is UserNotFound -> call.respond(
                    status = HttpStatusCode.NotFound,
                    message = mapOf("error" to cause.error)
                )

                is InvalidCredentials -> call.respond(
                    status = HttpStatusCode.Conflict,
                    message = mapOf("error" to cause.error)
                )

                is InvalidRefreshToken -> call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = mapOf("error" to cause.error)
                )

                is AlreadyHaveChannel -> call.respond(
                    status = HttpStatusCode.Conflict,
                    message = mapOf("error" to cause.error)
                )

                is NoChannelIdFound -> call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to cause.error)
                )

                is NoCommonChannelBetweenUsers -> call.respond(
                    status = HttpStatusCode.Conflict,
                    message = mapOf("error" to cause.error)
                )

                is UnexpectedError -> call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = mapOf("error" to cause.error)
                )
            }
        }
    }
}