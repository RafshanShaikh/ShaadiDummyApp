package com.example.myandroidtest.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
Created By Rafsan Shaikh
Date: 6-10-22
 **/
@Dao
interface ResultDAO {

    @Insert
    fun insertResult(result: List<ResultEntitiy>)

    @Update
    fun updateResult(result: ResultEntitiy)

    @Delete
    fun deleteResult(result: ResultEntitiy)

    @Query("SELECT * FROM result")
    fun getResults(): List<ResultEntitiy>
}