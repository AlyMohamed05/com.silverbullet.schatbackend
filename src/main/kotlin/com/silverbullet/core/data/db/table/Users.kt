package com.silverbullet.core.data.db.table

import com.silverbullet.core.data.db.table.ref.ChannelMemberships
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val username = varchar("username", length = 16).uniqueIndex()
    val name = varchar("name", length = 32)
    val password = text("password")
    val salt = text("salt")
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var name by Users.name
    var password by Users.password
    var salt by Users.salt
    val channels by Channel via ChannelMemberships
}