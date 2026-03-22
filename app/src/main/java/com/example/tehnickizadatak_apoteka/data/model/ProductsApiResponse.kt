package com.example.tehnickizadatak_apoteka.data.model


 data class ProductsApiResponse(
     val products: List<ProductResponse>,
     val total: Int,
     val skip: Int,
     val limit: Int
 )
