package com.silverbullet.feature_userinfo.route

import com.silverbullet.feature_userinfo.UserInfoController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureUserInfoRoute() {

    val userInfoController by inject<UserInfoController>()

    route("user") {

        onlineRoute(controller = userInfoController)
    }
}