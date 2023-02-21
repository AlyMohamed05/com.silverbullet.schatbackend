package com.silverbullet.core.data.db.utils

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.postgresql.util.PSQLException

fun ExposedSQLException.toDbError(): DbError{
    if(cause !is PSQLException){
        return DbError.UNKNOWN
    }
    val exception = cause as PSQLException
    return when(exception.sqlState){
        "23505" -> DbError.DUPLICATE_KEY
        else -> DbError.UNKNOWN
    }
}