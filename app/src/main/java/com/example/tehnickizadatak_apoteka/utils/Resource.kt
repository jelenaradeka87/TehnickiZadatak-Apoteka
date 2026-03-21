package com.example.tehnickizadatak_apoteka.utils

sealed class Resource<T> {

    class Loading<T> : Resource<T>()

    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error<T>(
        val message: String,
        val throwable: Throwable? = null
    ) : Resource<T>()
}