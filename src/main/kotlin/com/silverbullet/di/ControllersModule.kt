package com.silverbullet.di

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_channels.ChannelsController
import com.silverbullet.feature_connect.ConnectionsController
import com.silverbullet.feature_messages.MessagesController
import org.koin.dsl.module

val controllersModule = module {

    single {
        AuthenticationController(get(), get(), get(), get())
    }

    single {
        ChannelsController(get())
    }

    single {
        MessagesController(get(), get(), get(), get())
    }

    single {
        ConnectionsController(get(), get(), get(), get())
    }
}