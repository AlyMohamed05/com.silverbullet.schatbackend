package com.silverbullet.core.model

data class User(
    val username: String,
    val name: String,
    val password: String,
    val salt: String,
    val id: Int
)
