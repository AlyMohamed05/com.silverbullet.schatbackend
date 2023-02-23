package com.silverbullet.core.events

import io.ktor.websocket.*
import kotlinx.coroutines.delay

suspend fun WebSocketSession.startPingPong(
    onPingFailed: suspend () -> Unit
) {
    while (true) {
        delay(15000)
        try {
            send(Frame.Ping(ByteArray(0)))
        } catch (e: Exception) {
            onPingFailed()
        }
    }
}