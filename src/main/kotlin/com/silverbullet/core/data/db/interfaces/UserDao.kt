package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.entity.UserEntity
import com.silverbullet.core.model.UserInfo

interface UserDao {

    suspend fun createUser(
        username: String,
        name: String,
        password: String,
        salt: String
    ): DbResult<UserInfo>

    suspend fun getUser(username: String): DbResult<UserEntity?>
}