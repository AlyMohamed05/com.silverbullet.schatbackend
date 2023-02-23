package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.table.UsersTable
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
                UsersTable.insert {
                    it[UsersTable.username] = username
                    it[UsersTable.name] = name
                    it[UsersTable.password] = password
                    it[UsersTable.salt] = salt
                }
                DbResult.Success(UserInfo(username = username, name = name))
            } catch (e: ExposedSQLException) {
                DbResult.Failed(e.toDbError())
            }
        }

    override suspend fun getUser(username: String): DbResult.Success<UserEntity?> {
        return try {
            val user = dbQuery {
                UsersTable.select {
                    UsersTable.username eq username
                }
                    .singleOrNull()
                    ?.toUser()
            }
            DbResult.Success(user)
        } catch (e: ExposedSQLException) {
            DbResult.Success(null)
        }
    }

    private fun ResultRow.toUser(): UserEntity {
        return UserEntity(
            username = this[UsersTable.username],
            name = this[UsersTable.name],
            password = this[UsersTable.password],
            salt = this[UsersTable.salt],
            id = this[UsersTable.id].value
        )
    }
}