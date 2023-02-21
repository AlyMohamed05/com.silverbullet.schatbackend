package com.silverbullet.utils

/**
 * All of childs of this exception are handled in status pages plugin
 */
abstract class ApplicationException(val error: String): Exception()

class InvalidRequestBody : ApplicationException(error = "invalid request body")

class UsernameAlreadyExists: ApplicationException(error = "username already exists")

class UserNotFound: ApplicationException(error = "user not found")

class InvalidCredentials: ApplicationException(error = "invalid login credentials")

class InvalidRefreshToken: ApplicationException(error = "invalid refresh token")

class UnexpectedError: ApplicationException(error = "unexpected error occurred")
