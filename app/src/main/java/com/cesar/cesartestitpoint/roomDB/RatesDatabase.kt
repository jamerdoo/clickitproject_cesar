package com.cesar.cesartestitpoint.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cesar.cesartestitpoint.domain.entity.local.LocalRates
import com.cesar.cesartestitpoint.utils.Consts

@Database(entities = [LocalRates::class], version = 3)
abstract class RatesDatabase : RoomDatabase(){

    abstract val dao: RatesDao

    companion object {

        @Volatile
        private var INSTANCE: RatesDatabase? = null

        fun getInstance(context: Context): RatesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RatesDatabase::class.java,
                        Consts.Constants.databaseName
                    ).build()
                }
                return instance
            }
        }

    }
}