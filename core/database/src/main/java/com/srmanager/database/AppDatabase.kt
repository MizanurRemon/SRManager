package com.srmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.dao.UserDao
import com.srmanager.database.entity.LocationEntity
import com.srmanager.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, LocationEntity::class],
    exportSchema = false,
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val locationDao: LocationDao
}