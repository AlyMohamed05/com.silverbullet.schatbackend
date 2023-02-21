package com.silverbullet.core.data.db.utils

sealed interface DbResult<T> {

    class Success<T>(val data: T): DbResult<T>

    class Failed<Unit>(val error: DbError): DbResult<Unit>
}