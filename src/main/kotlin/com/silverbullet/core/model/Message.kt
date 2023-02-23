package com.silverbullet.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val senderId: Int,
    val receiverId: Int,
    val channelId: Int,
    val text: String,
    val id: String
)
