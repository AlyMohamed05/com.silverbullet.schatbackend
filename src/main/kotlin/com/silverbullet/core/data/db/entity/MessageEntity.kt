package com.silverbullet.core.data.db.entity

data class MessageEntity(
    val senderId: Int,
    val receiverId: Int,
    val channelId: Int,
    val text: String,
    val timestamp: Long
)
