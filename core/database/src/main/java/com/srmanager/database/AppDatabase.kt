package com.srmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srmanager.database.dao.UserDao
import com.srmanager.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    exportSchema =false ,
    version = 6
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}