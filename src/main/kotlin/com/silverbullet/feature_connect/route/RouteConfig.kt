package com.silverbullet.feature_connect.route

import com.silverbullet.feature_connect.ConnectionsController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureConnectionsRoute() {

    val connectionsController by inject<ConnectionsController>()

    route("connections") {

        createConnectionRoute(controller = connectionsController)
    }
}