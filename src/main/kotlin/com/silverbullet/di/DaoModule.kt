package com.silverbullet.di

import com.silverbullet.core.data.db.dao.*
import com.silverbullet.core.data.db.interfaces.*
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

    single<ConnectionsDao>{
        ConnectionsDaoImpl()
    }
}