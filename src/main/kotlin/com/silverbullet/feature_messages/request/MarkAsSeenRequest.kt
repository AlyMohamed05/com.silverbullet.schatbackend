package com.silverbullet.feature_messages.request

import kotlinx.serialization.Serializable

@Serializable
data class MarkAsSeenRequest(
    val messageId: String
)
