package com.silverbullet.core.data.db.utils

import com.silverbullet.core.data.db.table.ChannelsTable
import com.silverbullet.core.data.db.table.UsersTable
import com.silverbullet.core.data.db.table.ref.ChannelMembershipsTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Connects to database and Creates db tables if not created yet
 */
fun Application.configureDb() {
    val config = environment.config
    val driverClassName = config.property("storage.driverClassName").getString()
    val jdbcURL = config.property("storage.jdbcURL").getString()
    val user = config.property("storage.user").getString()
    val password = config.property("storage.password").getString()
    Database.connect(
        url = jdbcURL,
        driver = driverClassName,
        user = user,
        password = password
    )
    transaction {
        SchemaUtils.create(UsersTable,ChannelsTable,ChannelMembershipsTable)
    }
}