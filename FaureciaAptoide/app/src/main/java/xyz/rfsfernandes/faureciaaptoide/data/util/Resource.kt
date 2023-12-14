package xyz.rfsfernandes.faureciaaptoide.data.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String? = null, val errorCode: Int? = null) :
        Resource<T>(message = message)

    class NetworkError<T>(message: String) : Resource<T>(message = message)
    class Default<T> : Resource<T>()
}
