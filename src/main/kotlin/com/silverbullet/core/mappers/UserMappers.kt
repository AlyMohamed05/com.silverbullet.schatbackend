package com.silverbullet.core.mappers

import com.silverbullet.core.data.db.entity.UserEntity
import com.silverbullet.core.model.UserInfo

fun UserEntity.toUserInfo(): UserInfo =
    UserInfo(
        username = username,
        name = name
    )