package com.silverbullet.feature_auth.route

import com.silverbullet.feature_auth.AuthenticationController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureAuthenticationRoutes() {

    val authController by inject<AuthenticationController>()

    route("auth") {
        signupRoute(controller = authController)
        loginRoute(controller = authController)
        refreshTokenRoute(controller = authController)
        logoutRoute(controller = authController)
        validateUsernameRoute(controller = authController)
    }
}