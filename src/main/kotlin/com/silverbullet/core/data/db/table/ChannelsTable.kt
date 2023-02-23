package com.silverbullet.core.data.db.table

import com.silverbullet.core.data.db.table.ref.ChannelMembershipsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ChannelsTable: IntIdTable()

class Channel(id: EntityID<Int>): IntEntity(id){

    companion object: IntEntityClass<Channel>(ChannelsTable)

    var members by User via ChannelMembershipsTable
}