package com.srmanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.srmanager.database.entity.LocationEntity
@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationEntity: LocationEntity)
}