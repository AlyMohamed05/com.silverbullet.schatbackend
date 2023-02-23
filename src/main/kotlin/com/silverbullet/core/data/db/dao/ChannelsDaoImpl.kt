package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.table.Channel
import com.silverbullet.core.data.db.table.User
import com.silverbullet.core.data.db.table.Users
import com.silverbullet.core.data.db.table.ref.ChannelMemberships
import com.silverbullet.core.data.db.utils.DbError
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.utils.dbQuery
import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.core.model.UserInfo
import org.jetbrains.exposed.sql.insert
import org.litote.kmongo.coroutine.CoroutineDatabase

class ChannelsDaoImpl(
    db: CoroutineDatabase
) : ChannelsDao {

    private val messagesCollection = db.getCollection<MessageEntity>()

    override suspend fun createChannel(): DbResult<Int> =
        dbQuery {
            val newChannelId = Channel.new { }.id.value
            DbResult.Success(newChannelId)
        }

    override suspend fun addUserToChannel(
        userId: Int,
        channelId: Int
    ): DbResult<Unit> = dbQuery {
        ChannelMemberships.insert {
            it[user] = userId
            it[channel] = channelId
        }
        DbResult.Success(Unit)
    }

    override suspend fun alreadyHaveCommonChannel(
        user1Id: Int,
        user2Id: Int
    ): DbResult.Success<Boolean> =
        dbQuery {
            val user1 = User.find { Users.id eq user1Id }.first()
            user1
                .channels
                .forEach { channel ->
                    if (channel.members.any { user -> user.id.value == user2Id })
                        return@dbQuery DbResult.Success(true)
                }
            DbResult.Success(false)
        }

    override suspend fun getCommonChannel(user1Id: Int, user2Id: Int): DbResult.Success<Int?> =
        dbQuery {
            val user1 = User.findById(user1Id)
            val commonChannel = user1
                ?.channels
                ?.find { ch ->
                    ch.members.any { it.id.value == user2Id }
                }

            DbResult.Success(commonChannel?.id?.value)
        }


    override suspend fun sendMessage(message: MessageEntity): DbResult<Unit> =
        dbQuery {
            messagesCollection.insertOne(message)
            DbResult.Success(Unit)
        }

    override suspend fun getUserChannels(
        userId: Int
    ): DbResult<List<ChannelInfo>> =
        dbQuery {
            val user = User.findById(userId) ?: return@dbQuery DbResult.Failed(DbError.USER_NOT_FOUND)
            val channels = user
                .channels
                .map { ch ->
                    val channelUsers = mutableListOf<UserInfo>()
                    ch.members.forEach {
                        channelUsers.add(
                            UserInfo(username = it.username, name = it.name)
                        )
                    }
                    ChannelInfo(
                        id = ch.id.value,
                        members = channelUsers
                    )
                }
            DbResult.Success(channels)
        }
}