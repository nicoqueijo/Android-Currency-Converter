package com.nicoqueijo.android.network.model

/**
 * A sealed interface representing the result of an API operation.
 *
 * @param T The type of data returned by the API operation.
 */
sealed interface ApiOperation<T> {
    /**
     * Represents a successful API operation.
     *
     * @property data The data returned by the API operation.
     */
    data class Success<T>(val data: T) : ApiOperation<T>

    /**
     * Represents a failed API operation.
     *
     * @property exception The exception thrown during the API operation.
     */
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    /**
     * Executes the given block if the operation was successful.
     *
     * @param block A suspend function to be executed with the data if the operation was successful.
     * @return The current [ApiOperation] instance which can be used for chaining.
     */
    suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    /**
     * Executes the given block if the operation failed.
     *
     * @param block A function to be executed with the exception if the operation failed.
     * @return The current [ApiOperation] instance which can be used for chaining.
     */
    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) {
            block(exception)
        }
        return this
    }
}