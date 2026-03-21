package com.example.tehnickizadatak_apoteka.data.local


import androidx.room.TypeConverter
import com.example.tehnickizadatak_apoteka.data.model.Dimensions
import com.example.tehnickizadatak_apoteka.data.model.Meta
import com.example.tehnickizadatak_apoteka.data.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDimensions(dimensions: Dimensions): String = gson.toJson(dimensions)

    @TypeConverter
    fun toDimensions(value: String): Dimensions = gson.fromJson(value, Dimensions::class.java)

    @TypeConverter
    fun fromMeta(meta: Meta): String = gson.toJson(meta)

    @TypeConverter
    fun toMeta(value: String): Meta = gson.fromJson(value, Meta::class.java)

    @TypeConverter
    fun fromReviews(reviews: List<Review>): String = gson.toJson(reviews)

    @TypeConverter
    fun toReviews(value: String): List<Review> =
        gson.fromJson(value, object : TypeToken<List<Review>>() {}.type)

    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
}