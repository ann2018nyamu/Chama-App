package com.ekenya.echama.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ekenya.echama.model.GroupInvite
import com.ekenya.echama.model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Query("DELETE FROM chama_transaction")
    suspend fun deleteAll()
    //fetches all activities
    @Query("SELECT * FROM chama_transaction")
    fun getAllTrx(): LiveData<List<Transaction>>

    //fetches all user activities
     @Query("SELECT * FROM chama_transaction WHERE debitaccount = :userPhone ")
     fun getAllUserTrx(userPhone:String): LiveData<List<Transaction>>

    //fetches all group activities
    @Query("SELECT * FROM chama_transaction WHERE groupid = :groupid ")
    fun getAllGroupTrx(groupid:Int): LiveData<List<Transaction>>

     //fetches all contribution activities
    @Query("SELECT * FROM chama_transaction WHERE contributionid = :contributionid ")
    fun getAllContributionTrx(contributionid:Int): LiveData<List<Transaction>>


}