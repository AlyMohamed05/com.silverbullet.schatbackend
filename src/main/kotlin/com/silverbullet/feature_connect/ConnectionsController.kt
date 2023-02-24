package com.silverbullet.feature_connect

import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.interfaces.ConnectionsDao
import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.events.EventsDispatcher
import com.silverbullet.feature_auth.UserNotFound
import com.silverbullet.utils.UnexpectedError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ConnectionsController(
    private val userDao: UserDao,
    private val channelsDao: ChannelsDao,
    private val connectionsDao: ConnectionsDao,
    private val eventsDispatcher: EventsDispatcher
) {

    /**
     * @param userId the userId who made the request
     * @param username the username which the requester wants to connect with
     */
    suspend fun connectUsers(
        userId: Int,
        username: String
    ) {
        val targetUser = userDao.getUser(username).data ?: throw UserNotFound()
        if (connectionsDao.areAlreadyConnected(userId, targetUser.id).data)
            throw AlreadyConnectedUsers()
        val connectionResult = connectionsDao.connectUser(userId, targetUser.id)
        if (connectionResult is DbResult.Failed)
            throw UnexpectedError()
        val channelResult =
            channelsDao.createChannel() as? DbResult.Success ?: throw UnexpectedError() // Shouldn't fail
        val channelId = channelResult.data
        channelsDao.addUserToChannel(userId = userId, channelId = channelId)
        channelsDao.addUserToChannel(userId = targetUser.id, channelId = channelId)
        coroutineScope {
            launch {
                eventsDispatcher.onCreateChannel(channelId = channelId, userId, targetUser.id)
            }
        }
    }
}