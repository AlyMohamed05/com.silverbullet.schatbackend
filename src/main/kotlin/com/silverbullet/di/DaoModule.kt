package com.silverbullet.di

import com.silverbullet.core.data.db.dao.RefreshTokenDaoImpl
import com.silverbullet.core.data.db.dao.UserDaoImpl
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
}