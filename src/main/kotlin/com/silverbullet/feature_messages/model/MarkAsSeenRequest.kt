package com.silverbullet.feature_messages.model

import kotlinx.serialization.Serializable

@Serializable
data class MarkAsSeenRequest(
    val messageId: String
)
