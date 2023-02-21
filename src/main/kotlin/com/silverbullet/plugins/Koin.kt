package com.silverbullet.plugins

import com.silverbullet.di.appModule
import com.silverbullet.di.controllersModule
import com.silverbullet.di.daoModule
import com.silverbullet.utils.getJwtTokenConfigFromEnv
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoinDi() {
    install(Koin) {
        slf4jLogger()
        modules(
            appModule,
            controllersModule,
            daoModule,
            module { single { getJwtTokenConfigFromEnv() } }
        )
    }
}