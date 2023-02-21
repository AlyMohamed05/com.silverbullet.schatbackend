package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.User

interface UserDao {

    suspend fun createUser(
        username: String,
        name: String,
        password: String,
        salt: String
    ): DbResult<Unit>

    suspend fun getUser(username: String): DbResult<User?>
}