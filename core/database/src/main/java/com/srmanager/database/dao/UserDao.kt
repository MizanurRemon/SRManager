package com.srmanager.database.dao

import androidx.room.*
import com.srmanager.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE  FROM user")
    suspend fun deleteUsers()

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("UPDATE user SET email = :email")
    fun updateEmail(email: String)

}