package com.silverbullet.di

import com.silverbullet.core.data.db.dao.ChannelsDaoImpl
import com.silverbullet.core.data.db.dao.MessagesDaoImpl
import com.silverbullet.core.data.db.dao.RefreshTokenDaoImpl
import com.silverbullet.core.data.db.dao.UserDaoImpl
import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.interfaces.MessagesDao
import com.silverbullet.core.data.db.interfaces.RefreshTokenDao
import com.silverbullet.core.data.db.interfaces.UserDao
import org.koin.dsl.module

val daoModule = module {
    single<UserDao> {
        UserDaoImpl()
    }

    single<RefreshTokenDao> {
        RefreshTokenDaoImpl(get())
    }

    single<ChannelsDao> {
        ChannelsDaoImpl(get())
    }

    single<MessagesDao> {
        MessagesDaoImpl(get())
    }
}