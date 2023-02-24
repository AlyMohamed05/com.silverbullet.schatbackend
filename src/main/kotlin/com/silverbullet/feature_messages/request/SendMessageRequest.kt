package com.silverbullet.feature_messages.request

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val receiverUsername: String,
    val text: String
)
