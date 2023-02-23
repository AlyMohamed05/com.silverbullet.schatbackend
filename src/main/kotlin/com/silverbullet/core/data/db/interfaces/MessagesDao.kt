package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.Message

interface MessagesDao {

    suspend fun getChannelMessages(channelId: Int): DbResult.Success<List<Message>>
}