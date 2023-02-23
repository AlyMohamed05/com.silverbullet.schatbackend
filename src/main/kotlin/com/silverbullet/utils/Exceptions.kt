package com.silverbullet.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * All of childs of this exception are handled in status pages plugin
 */
abstract class ApplicationException(
    val error: String,
    private val httpStatusCode: HttpStatusCode,
    private val errorCode: Int
) : Exception() {

    suspend fun handle(call: ApplicationCall) {
        call.respond(
            status = httpStatusCode,
            message = ErrorMessage(
                error = error,
                errorCode = errorCode
            )
        )
    }
}

class UnexpectedError : ApplicationException(
    error = "unexpected error occurred",
    httpStatusCode = HttpStatusCode.InternalServerError,
    errorCode = ApplicationErrorCodes.UnexpectedErrorCode
)

class InvalidRequestBody : ApplicationException(
    error = "invalid request body",
    httpStatusCode = HttpStatusCode.BadRequest,
    errorCode = ApplicationErrorCodes.InvalidRequestBodyCode
)
