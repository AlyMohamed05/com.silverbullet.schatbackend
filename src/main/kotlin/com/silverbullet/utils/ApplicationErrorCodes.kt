package com.silverbullet.utils

object ApplicationErrorCodes {

    const val UnexpectedErrorCode = 0

    const val InvalidRequestBodyCode = 1

    // Authentication routes Codes
    const val UsernameAlreadyExistsCode = 2

    const val UserNotFoundCode = 3

    const val InvalidCredentialsCode = 4

    const val InvalidRefreshTokenCode = 5

    const val InvalidUsername = 6

    // Channel routes Codes
    const val AlreadyHaveChannelCode = 7

    const val NoChannelIdFoundCode = 8

    const val NoCommonChannelBetweenUsersCode = 9
}