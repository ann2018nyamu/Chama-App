package com.ekenya.echama.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ekenya.echama.model.Country
import com.ekenya.echama.model.User

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: Country)

    @Query("DELETE FROM country")
    suspend fun deleteAll()

    @Query("SELECT * FROM country")
     fun getCountries(): LiveData<List<Country>>

}