package com.silverbullet.feature_messages

import com.silverbullet.utils.ApplicationErrorCodes
import com.silverbullet.utils.ApplicationException
import io.ktor.http.*

class MessageNotFoundException : ApplicationException(
    error = "message not found",
    httpStatusCode = HttpStatusCode.NotFound,
    errorCode = ApplicationErrorCodes.MESSAGE_NOT_FOUND
)