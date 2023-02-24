package com.silverbullet.di

import com.silverbullet.core.events.DefaultEventsDispatcher
import com.silverbullet.core.events.EventsDispatcher
import com.silverbullet.core.security.hashing.SHA256HashingService
import com.silverbullet.core.security.token.JwtTokenService
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {

    single {
        SHA256HashingService()
    }

    single {
        JwtTokenService(get())
    }

    // Injecting the Coroutine db version
    single {
        KMongo.createClient().coroutine.getDatabase("s_chat_db")
    }

    single<EventsDispatcher> {
        DefaultEventsDispatcher(get())
    }
}