package com.example.myandroidtest.webservice

import com.example.myandroidtest.model.Dummy
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://randomuser.me/api/?results=10

const val BASE_URL = "https://randomuser.me/"

interface ShaadiInterface {

    //get request
    @GET("api/")
    fun getUserList(@Query("results") result:Int):Call<Dummy>

}

object DataService{
    val shaadiInterface: ShaadiInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        shaadiInterface = retrofit.create(ShaadiInterface::class.java)
    }

}