package com.silverbullet.core.mappers

import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.model.Message

fun MessageEntity.toMessage(): Message =
    Message(
        senderId = senderId,
        receiverId = receiverId,
        channelId = channelId,
        text = text,
        id = id
    )