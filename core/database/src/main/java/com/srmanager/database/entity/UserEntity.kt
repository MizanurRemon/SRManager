package com.srmanager.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int? = null,
    val email: String? = null,
    val userName: String? = null
)