package com.silverbullet.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ChannelInfo(
    val id: Int,
    val members: List<UserInfo>
)
