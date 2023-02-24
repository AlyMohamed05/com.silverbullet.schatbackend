package com.silverbullet.core.data.db.table.ref

import com.silverbullet.core.data.db.table.UsersTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ConnectionsTable : Table() {

    val user1 = reference(
        "user1",
        UsersTable,
        onDelete = ReferenceOption.CASCADE,
        fkName = "FK_USER1"
    )

    val user2 = reference(
        "user2",
        UsersTable,
        onDelete = ReferenceOption.CASCADE,
        fkName = "FK_USER2"
    )

    override val primaryKey = PrimaryKey(user1, user2)
}