package com.alfin.simplecartapp.core.data.state

import com.alfin.simplecartapp.core.data.network.Resource


/**
 * A network response handler.
 * @param success
 * @param error
 * @param I input
 * @param O output
 */
fun <I, O> Resource<I>.handle(success: () -> O, error: () -> O, loading: () -> O): O {
    return when (this) {
        is Resource.Success<I> -> success.invoke()
        is Resource.Error<I> -> error.invoke()
        is Resource.Loading<I> -> loading.invoke()
    }
}