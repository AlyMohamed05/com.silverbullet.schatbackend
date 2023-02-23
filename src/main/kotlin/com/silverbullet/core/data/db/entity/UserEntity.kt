package com.silverbullet.core.data.db.entity

data class UserEntity(
    val username: String,
    val name: String,
    val password: String,
    val salt: String,
    val id: Int
)
