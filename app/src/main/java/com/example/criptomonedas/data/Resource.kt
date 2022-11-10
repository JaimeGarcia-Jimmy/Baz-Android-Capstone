package com.example.criptomonedas.data

sealed class Resource<T> {
    data class Loading<T>( val message: String ): Resource<T>()
    data class Success<T>( val data: T ): Resource<T>()
    data class Error<T>( val message: String, val exception: Exception ? ): Resource<T>()

    companion object {
        fun<T> loading(message: String = "Cargando...") = Loading<T>(message)
        fun<T> success(data: T) = Success(data)
        fun<T> error(message: String, exception: Exception? = null) = Error<T>(message, exception)
    }
}
