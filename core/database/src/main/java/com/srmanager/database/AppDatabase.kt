package com.srmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.dao.ProductsDao
import com.srmanager.database.dao.UserDao
import com.srmanager.database.entity.LocationEntity
import com.srmanager.database.entity.ProductsEntity
import com.srmanager.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, LocationEntity::class, ProductsEntity::class],
    exportSchema = false,
    version = 10
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val locationDao: LocationDao
    abstract val productsDao: ProductsDao
}