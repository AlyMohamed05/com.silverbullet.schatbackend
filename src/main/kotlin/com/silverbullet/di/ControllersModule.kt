package com.silverbullet.di

import com.silverbullet.feature_auth.AuthenticationController
import com.silverbullet.feature_channels.ChannelsController
import com.silverbullet.feature_messages.MessagesController
import org.koin.dsl.module

val controllersModule = module {

    single {
        AuthenticationController(get(), get(), get(), get())
    }

    single {
        ChannelsController(get(), get(), get())
    }

    single {
        MessagesController(get(), get(), get(), get())
    }
}