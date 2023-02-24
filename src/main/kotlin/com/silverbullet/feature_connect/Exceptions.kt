package com.silverbullet.feature_connect

import com.silverbullet.utils.ApplicationErrorCodes
import com.silverbullet.utils.ApplicationException
import io.ktor.http.*

class AlreadyConnectedUsers : ApplicationException(
    error = "you are already connected",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.AlreadyConnectedUsers
)