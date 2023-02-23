package com.silverbullet.utils

object ApplicationErrorCodes {

    const val UnexpectedErrorCode = 0

    const val InvalidRequestBodyCode = 1

    // Authentication routes Codes
    const val UsernameAlreadyExistsCode = 2

    const val UserNotFoundCode = 3

    const val InvalidCredentialsCode = 4

    const val InvalidRefreshTokenCode = 5

    // Channel routes Codes
    const val AlreadyHaveChannelCode = 6

    const val NoChannelIdFoundCode = 7

    const val NoCommonChannelBetweenUsersCode = 8
}