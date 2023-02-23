package com.silverbullet.feature_channels.model

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val receiverUsername: String,
    val text: String
)
