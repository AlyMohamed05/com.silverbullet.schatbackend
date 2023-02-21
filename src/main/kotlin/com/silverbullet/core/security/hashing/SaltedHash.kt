package com.silverbullet.core.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
