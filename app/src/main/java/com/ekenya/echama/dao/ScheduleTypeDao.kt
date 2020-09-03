package com.ekenya.echama.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ekenya.echama.model.GroupInvite
import com.ekenya.echama.model.ScheduleType
import com.ekenya.echama.model.Transaction

@Dao
interface ScheduleTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scheduleType: ScheduleType)

    @Query("DELETE FROM schedule_type")
    suspend fun deleteAll()
    //fetches all activities
    @Query("SELECT * FROM schedule_type")
    fun getAllScheduleType(): LiveData<List<ScheduleType>>


}