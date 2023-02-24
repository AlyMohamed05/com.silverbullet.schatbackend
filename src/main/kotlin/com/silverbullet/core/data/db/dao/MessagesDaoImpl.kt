package com.silverbullet.core.data.db.dao

import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.data.db.interfaces.MessagesDao
import com.silverbullet.core.data.db.utils.DbError
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.utils.dbQuery
import com.silverbullet.core.mappers.toMessage
import com.silverbullet.core.model.Message
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MessagesDaoImpl(
    db: CoroutineDatabase
) : MessagesDao {

    private val messagesCollection = db.getCollection<MessageEntity>()

    override suspend fun getChannelMessages(channelId: Int): DbResult.Success<List<Message>> {
        val messages = messagesCollection
            .find(MessageEntity::channelId eq channelId)
            .ascendingSort(MessageEntity::timestamp)
            .toList()
            .map(MessageEntity::toMessage)
        return DbResult.Success(messages)
    }

    override suspend fun sendMessage(message: MessageEntity): DbResult<Unit> =
        dbQuery {
            messagesCollection.insertOne(message)
            DbResult.Success(Unit)
        }

    override suspend fun markMessageAsSeen(
        receiverId: Int,
        messageId: String
    ): DbResult<Message> {
        val message = messagesCollection
            .findOneAndUpdate(
                filter = and(
                    MessageEntity::id eq messageId,
                    MessageEntity::receiverId eq receiverId
                ),
                setValue(MessageEntity::seen, true),
                options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            ) ?: return DbResult.Failed(DbError.MESSAGE_NOT_FOUND)
        return DbResult.Success(message.toMessage())
    }
}