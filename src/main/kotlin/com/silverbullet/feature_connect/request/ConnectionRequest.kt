package com.silverbullet.feature_connect.request

import kotlinx.serialization.Serializable

/**
 * @param username username the sender wants to connect to
 */
@Serializable
data class ConnectionRequest(
    val username: String
)
