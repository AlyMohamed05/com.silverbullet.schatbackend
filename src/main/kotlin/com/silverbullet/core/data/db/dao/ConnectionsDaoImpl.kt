package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.ConnectionsDao
import com.silverbullet.core.data.db.table.UsersTable
import com.silverbullet.core.data.db.table.ref.ConnectionsTable
import com.silverbullet.core.data.db.table.ref.ConnectionsTable.user1
import com.silverbullet.core.data.db.table.ref.ConnectionsTable.user2
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.utils.dbQuery
import com.silverbullet.core.data.db.utils.toDbError
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*

class ConnectionsDaoImpl : ConnectionsDao {

    override suspend fun connectUser(
        user1Id: Int,
        user2Id: Int
    ): DbResult<Unit> = dbQuery {
        try {
            // Store the smaller id first
            // To avoid duplications, now we now that to check if a connection is already created
            // then the smaller id must be stored first
            if (user1Id < user2Id) {
                ConnectionsTable.insert {
                    it[user1] = user1Id
                    it[user2] = user2Id
                }
            } else {
                ConnectionsTable.insert {
                    it[user1] = user2Id
                    it[user2] = user1Id
                }
            }
            DbResult.Success(Unit)
        } catch (e: ExposedSQLException) {
            DbResult.Failed(e.toDbError())
        }
    }

    override suspend fun areAlreadyConnected(
        user1Id: Int,
        user2Id: Int
    ): DbResult.Success<Boolean> = dbQuery {
        // Notice that we are always storing the smalled id first, so we just need to query for this pattern
        val smallerId = if (user1Id < user2Id) user1Id else user2Id
        val largerId = if (user1Id > user2Id) user1Id else user2Id
        val connectionExists = ConnectionsTable
            .select { (user1 eq smallerId) and (user2 eq largerId) }
            .count() > 0
        DbResult.Success(connectionExists)
    }

    override suspend fun getUserConnectionsIds(userId: Int): DbResult<List<Int>> =
        dbQuery {
            val connections = ConnectionsTable
                .select { (user1 eq userId) or (user2 eq userId) }
                .map {
                    if (it[user1].value == userId)
                        it[user2].value
                    else
                        it[user1].value
                }
            val userConnections = UsersTable
                .select { UsersTable.id inList connections }
                .map { it[UsersTable.id].value }

            DbResult.Success(userConnections)
        }
}