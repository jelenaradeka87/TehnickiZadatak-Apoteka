package com.example.tehnickizadatak_apoteka.data.remote

import com.example.tehnickizadatak_apoteka.data.model.ProductResponse
import com.example.tehnickizadatak_apoteka.data.model.ProductsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): ProductsApiResponse

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductsApiResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductResponse
}