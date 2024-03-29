package com.srmanager.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val latitude: String? = null,
    val longitude: String? = null,
    val address: String? = null
)
