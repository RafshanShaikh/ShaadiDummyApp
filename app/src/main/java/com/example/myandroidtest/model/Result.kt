package com.example.myandroidtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
data class Result(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)*/

data class Result(
    val id: Id,
    val dob: Dob,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val phone: String,
    val picture: Picture,
)