package com.silverbullet.core.data.db.table.ref

import com.silverbullet.core.data.db.table.ChannelsTable
import com.silverbullet.core.data.db.table.UsersTable
import org.jetbrains.exposed.sql.Table

object ChannelMembershipsTable: Table(){

    val channel = reference("channel", ChannelsTable)
    val user = reference("user",UsersTable)

    override val primaryKey = PrimaryKey(channel, user)
}