package com.silverbullet.core.events

import com.silverbullet.core.model.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Event

@Serializable
@SerialName("added_to_channel")
data class AddedToChannelEvent(
    val channelId: Int
): Event

@Serializable
@SerialName("received_message")
data class ReceivedMessage(
    val message: Message
): Event