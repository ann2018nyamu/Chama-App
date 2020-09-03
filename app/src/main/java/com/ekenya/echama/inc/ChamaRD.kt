package com.ekenya.echama.inc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ekenya.echama.dao.*
import com.ekenya.echama.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class,
    Country::class,Group::class,
    Transaction::class,
    Contribution::class,
    ScheduleType::class,
    ContributionType::class,
    AccountType::class,
    GroupAccount::class,
    Member::class,
    LoanApproved::class,
    LoanProduct::class,
    GroupInvite::class],
    version = 16, exportSchema = true)
abstract class ChamaRD : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
    abstract fun groupDao(): GroupDao
    abstract fun recentActivitiesDao(): TransactionDao
    abstract fun contributionDao(): ContributionDao
    abstract fun scheduleTypeDao(): ScheduleTypeDao
    abstract fun contributionTypeDao(): ContributionTypeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ChamaRD? = null


        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): ChamaRD {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChamaRD::class.java,
                        "chama_database"
                    ).addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(
                        database.userDao(),
                        database.groupDao(),
                        database.recentActivitiesDao(),
                        database.contributionDao(),
                        database.scheduleTypeDao(),
                        database.contributionTypeDao(),
                        database.countryDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao,
                                     groupDao: GroupDao,
                                     transactionDao: TransactionDao,
                                     contributionDao: ContributionDao,
                                     scheduleTypeDao: ScheduleTypeDao,
                                     contributionTypeDao: ContributionTypeDao,
                                     countryDao: CountryDao) {
            // Delete all content here.
           // userDao.deleteAll()
            groupDao.deleteAll()
            groupDao.deleteAllInvites()
            groupDao.deleteMembers()

            // TODO: Add your own words!
        }
    }
}