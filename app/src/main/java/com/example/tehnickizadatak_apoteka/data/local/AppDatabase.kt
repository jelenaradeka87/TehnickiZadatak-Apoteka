package com.example.tehnickizadatak_apoteka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tehnickizadatak_apoteka.data.local.dao.ProductDao
import com.example.tehnickizadatak_apoteka.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}