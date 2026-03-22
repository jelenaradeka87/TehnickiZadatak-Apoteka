package com.example.tehnickizadatak_apoteka.data.model

import com.example.tehnickizadatak_apoteka.data.local.entity.ProductEntity


data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String? = null,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String
)

fun ProductResponse.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    tags = tags,
    brand = brand.orEmpty(),
    sku = sku,
    weight = weight,
    dimensions = dimensions,
    warrantyInformation = warrantyInformation,
    shippingInformation = shippingInformation,
    availabilityStatus = availabilityStatus,
    reviews = reviews,
    returnPolicy = returnPolicy,
    minimumOrderQuantity = minimumOrderQuantity,
    meta = meta,
    images = images,
    thumbnail = thumbnail
)
