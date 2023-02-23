package com.silverbullet.feature_channels

import com.silverbullet.utils.ApplicationErrorCodes
import com.silverbullet.utils.ApplicationException
import io.ktor.http.*

class AlreadyHaveChannel : ApplicationException(
    error = "already have channel",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.AlreadyHaveChannelCode
)

class NoChannelIdFound : ApplicationException(
    error = "you must pass the channel id",
    httpStatusCode = HttpStatusCode.BadRequest,
    errorCode = ApplicationErrorCodes.NoChannelIdFoundCode
)

class NoCommonChannelBetweenUsers : ApplicationException(
    error = "these users are not connected",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.NoCommonChannelBetweenUsersCode
)