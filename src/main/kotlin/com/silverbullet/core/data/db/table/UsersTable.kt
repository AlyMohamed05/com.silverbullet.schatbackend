package com.silverbullet.core.data.db.table

import com.silverbullet.core.data.db.table.ref.ChannelMembershipsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable : IntIdTable() {
    val username = varchar("username", length = 16).uniqueIndex()
    val name = varchar("name", length = 32)
    val password = text("password")
    val salt = text("salt")
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UsersTable)

    var username by UsersTable.username
    var name by UsersTable.name
    var password by UsersTable.password
    var salt by UsersTable.salt
    val channels by Channel via ChannelMembershipsTable
}