package com.silverbullet.di

import com.silverbullet.feature_auth.AuthenticationController
import org.koin.dsl.module

val controllersModule = module {
    single {
        AuthenticationController(get(), get(), get(), get())
    }
}