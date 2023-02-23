package com.silverbullet.feature_userinfo

import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.events.EventsDispatcher
import com.silverbullet.feature_auth.UserNotFound

class UserInfoController(
    private val eventsDispatcher: EventsDispatcher,
    private val userDao: UserDao
) {

    suspend fun checkOnlineStatus(username: String): Boolean {
        val targetUserId = userDao.getUser(username).data?.id ?: throw UserNotFound()
        return eventsDispatcher.isUserOnline(targetUserId)
    }
}