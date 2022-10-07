package com.example.myandroidtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myandroidtest.adapter.Adapter
import com.example.myandroidtest.model.Dummy
import com.example.myandroidtest.model.Result
import com.example.myandroidtest.model.ResultDatabase
import com.example.myandroidtest.model.ResultEntitiy
import com.example.myandroidtest.webservice.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
Created By Rafsan Shaikh
Date: 6-10-22
 **/

class MainActivity : AppCompatActivity(), Adapter.CallbackInterface {

    lateinit var adapter: Adapter
    private lateinit var recycler_view: RecyclerView
    private var itemList = mutableListOf<Result>()
    private var itemResultList = mutableListOf<ResultEntitiy>()
    lateinit var database: ResultDatabase
    var memberStatus: String = "none"
    private var progressBar: ProgressBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar
        recycler_view = findViewById<RecyclerView>(R.id.results_list)
        adapter = Adapter(this@MainActivity, itemResultList)

        //setting listener on the interface
        adapter.setListener(this)

        //setting adapter on the recyclerview
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)

        //getting db instance through singleton object created.
        database = ResultDatabase.getInstance(this)

        //checking for internet connection and performing operations.
        if (checkForInternet(this)) {
            Toast.makeText(this, "Internet connected fetching from API", Toast.LENGTH_SHORT).show()
            //fetching from API
            getResults()
        } else {
            Toast.makeText(
                this,
                "Internet connection not available fetching from db",
                Toast.LENGTH_SHORT
            ).show()
            //fetching from database
            getData()
        }

    }

    private fun getData() {
        //fetching data from db
        progressBar!!.visibility = View.VISIBLE
        itemResultList = database.resultDao().getResults() as MutableList<ResultEntitiy>
        adapter = Adapter(this@MainActivity, itemResultList)
        adapter.setListener(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        progressBar!!.visibility = View.INVISIBLE

        adapter.notifyDataSetChanged()
    }

    private fun getResults() {
        val results = DataService.shaadiInterface.getUserList(10)

        progressBar!!.visibility = View.VISIBLE
        //Making an API Call
        results.enqueue(object : Callback<Dummy> {
            override fun onResponse(call: Call<Dummy>, response: Response<Dummy>) {
                progressBar!!.visibility = View.INVISIBLE

                val serviceResults = response.body()
                if (serviceResults != null) {
                    Log.d("RAFSAN", serviceResults.toString())

                    itemList.addAll(serviceResults.results)

                    var count = 0
                    while (count < itemList.size) {
                        itemResultList.addAll(
                            listOf(
                                ResultEntitiy(
                                    0,
                                    itemList[count].dob.date + " (Age : "+ itemList[count].dob.age + " )",
                                    itemList[count].email,
                                    itemList[count].gender,
                                    itemList[count].location.country + "-" + itemList[count].location.city,
                                    itemList[count].name.title + " " + itemList[count].name.first + " " + itemList[count].name.last,
                                    itemList[count].phone,
                                    itemList[count].picture.large,
                                    memberStatus
                                )
                            )
                        )
                        count++
                    }
                    //inserting data in the db
                    database.resultDao().insertResult(itemResultList)
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<Dummy>, t: Throwable) {
                Log.d("RAFSAN", "Error in fetching data", t)
            }
        })


    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    //fetching status from interface
    override fun passResultCallback(status: String) {
        this.memberStatus = status
    }

}