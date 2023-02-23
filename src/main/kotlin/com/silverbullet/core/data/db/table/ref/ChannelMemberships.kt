package com.silverbullet.core.data.db.table.ref

import com.silverbullet.core.data.db.table.Channels
import com.silverbullet.core.data.db.table.Users
import org.jetbrains.exposed.sql.Table

object ChannelMemberships: Table(){

    val channel = reference("channel", Channels)
    val user = reference("user",Users)

    override val primaryKey = PrimaryKey(channel, user)
}