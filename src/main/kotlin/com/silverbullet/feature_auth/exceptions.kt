package com.silverbullet.feature_auth

import com.silverbullet.utils.ApplicationErrorCodes
import com.silverbullet.utils.ApplicationException
import io.ktor.http.*

class UsernameAlreadyExists: ApplicationException(
    error = "username already exists",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.UsernameAlreadyExistsCode
)

class UserNotFound: ApplicationException(
    error = "user not found",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.UserNotFoundCode
)

class InvalidCredentials: ApplicationException(
    error = "invalid login credentials",
    httpStatusCode = HttpStatusCode.Conflict,
    errorCode = ApplicationErrorCodes.InvalidCredentialsCode
)

class InvalidRefreshToken: ApplicationException(
    error = "invalid refresh token",
    httpStatusCode = HttpStatusCode.Unauthorized,
    errorCode = ApplicationErrorCodes.InvalidRefreshTokenCode
)