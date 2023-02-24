package com.silverbullet.core.data.db.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class MessageEntity(
    val senderId: Int,
    val receiverId: Int,
    val channelId: Int,
    val text: String,
    val timestamp: Long,
    val seen: Boolean = false,
    @BsonId
    val id: String = ObjectId().toString()
)
