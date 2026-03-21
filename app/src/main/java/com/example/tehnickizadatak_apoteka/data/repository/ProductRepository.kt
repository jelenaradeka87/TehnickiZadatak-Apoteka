package com.example.tehnickizadatak_apoteka.data.repository

import com.example.tehnickizadatak_apoteka.data.local.dao.ProductDao
import com.example.tehnickizadatak_apoteka.data.local.entity.ProductEntity
import com.example.tehnickizadatak_apoteka.data.model.ProductResponse
import com.example.tehnickizadatak_apoteka.data.model.toEntity
import com.example.tehnickizadatak_apoteka.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ApiService,
    private val productDao: ProductDao
) {

    fun observeFavorites(): Flow<List<ProductEntity>> =
        productDao.getAllProducts()

    suspend fun addToFavorites(product: ProductResponse) {
        productDao.insertProduct(product.toEntity())
    }

    suspend fun getAllProducts(): List<ProductResponse> {
        return api.getAllProducts().products
    }

    suspend fun getProductsPage(limit: Int, skip: Int): List<ProductResponse> {
        return api.getAllProducts(limit, skip).products
    }

    suspend fun getProductById(id: Int): ProductResponse {
        return api.getProductById(id)
    }
}
