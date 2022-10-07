package com.example.myandroidtest.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myandroidtest.model.Result

/**
Created By Rafsan Shaikh
Date: 6-07-22
 **/
@Database(entities = [com.example.myandroidtest.model.ResultEntitiy::class], version = 1)
abstract class ResultDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDAO

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        //creating instance for db using singleton pattern
        fun getInstance(context: Context): ResultDatabase {
            if (INSTANCE == null) {
                //creating db instance
                //using the locking mechanism so that only one instance is created incase multiple thread tries to access it
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ResultDatabase::class.java,
                        "resultDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }
    }
}