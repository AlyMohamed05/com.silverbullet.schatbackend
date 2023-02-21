package com.silverbullet.core.data.db.table

import org.jetbrains.exposed.sql.Table

object Users: Table() {

    val id = integer("id").autoIncrement()
    val username = varchar("username", length = 16).uniqueIndex()
    val name = varchar("name", length = 32)
    val password = text("password")
    val salt = text("salt")

    override val primaryKey = PrimaryKey(id)
}