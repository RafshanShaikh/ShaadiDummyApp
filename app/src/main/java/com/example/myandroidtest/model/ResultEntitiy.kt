package com.example.myandroidtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created By Rafsan Shaikh
Date: 6-10-22
 **/
@Entity(tableName = "result")
data class ResultEntitiy(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dob: String,
    val email: String,
    val gender: String,
    val location: String,
    val name: String,
    val phone: String,
    val picture: String,
    val status:String
)