package com.silverbullet.feature_channels.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateChannelRequest(
    val username: String
)
