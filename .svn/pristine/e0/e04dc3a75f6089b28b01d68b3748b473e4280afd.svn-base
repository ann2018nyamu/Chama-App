package com.ekenya.echama.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ekenya.echama.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
     fun getUser(): LiveData<List<User>>
    /**
     * Updating only token
     * By order id
     */
    @Query("UPDATE user SET token=:token WHERE firstname = :firstname")
    fun updateToken(token: String, firstname: String)

}