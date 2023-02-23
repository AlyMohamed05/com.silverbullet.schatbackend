package com.silverbullet.core.mappers

import com.silverbullet.core.data.db.entity.ChannelEntity
import com.silverbullet.core.data.db.entity.UserEntity
import com.silverbullet.core.model.ChannelInfo

fun ChannelEntity.toChannelInfo() =
    ChannelInfo(
        id = id,
        members = users.map(UserEntity::toUserInfo)
    )