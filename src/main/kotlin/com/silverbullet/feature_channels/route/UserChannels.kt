package com.silverbullet.feature_channels.route

import com.silverbullet.feature_channels.ChannelsController
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userChannelsRoute(controller: ChannelsController){

    authenticate {

        get {

            val userId = call.userId ?: throw UnexpectedError()
            val channels = controller.getUserChannels(userId)
            call.respond(channels)
        }
    }
}