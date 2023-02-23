package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.table.Users
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.utils.dbQuery
import com.silverbullet.core.data.db.utils.toDbError
import com.silverbullet.core.data.db.entity.UserEntity
import com.silverbullet.core.model.UserInfo
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserDaoImpl : UserDao {

    override suspend fun createUser(
        username: String,
        name: String,
        password: String,
        salt: String
    ): DbResult<UserInfo> =
        dbQuery {
            try {
                Users.insert {
                    it[Users.username] = username
                    it[Users.name] = name
                    it[Users.password] = password
                    it[Users.salt] = salt
                }
                DbResult.Success(UserInfo(username = username, name = name))
            } catch (e: ExposedSQLException) {
                DbResult.Failed(e.toDbError())
            }
        }

    override suspend fun getUser(username: String): DbResult<UserEntity?> {
        return try {
            val user = dbQuery {
                Users.select {
                    Users.username eq username
                }
                    .singleOrNull()
                    ?.toUser()
            }
            DbResult.Success(user)
        } catch (e: ExposedSQLException) {
            DbResult.Failed(e.toDbError())
        }
    }

    private fun ResultRow.toUser(): UserEntity {
        return UserEntity(
            username = this[Users.username],
            name = this[Users.name],
            password = this[Users.password],
            salt = this[Users.salt],
            id = this[Users.id].value
        )
    }
}