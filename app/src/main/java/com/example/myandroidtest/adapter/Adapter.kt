package com.example.myandroidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myandroidtest.R
import com.example.myandroidtest.model.ResultEntitiy

/**
Created By Rafsan Shaikh
Date: 6-10-22
 **/
class Adapter(val context: Context, val itemList: List<ResultEntitiy>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<Adapter.MyViewHolder>() {

    lateinit var status: String
    private lateinit var callbackInterface: CallbackInterface

    fun setListener(listener : CallbackInterface){
        this.callbackInterface = listener;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.accepted_btn.setOnClickListener {
            holder.btn_layout_ll.visibility = View.GONE
            holder.accepted_txt.visibility = View.VISIBLE
            holder.declined_txt.visibility = View.GONE
            status = "accepted"
            callbackInterface.passResultCallback(status)
        }

        holder.declined_btn.setOnClickListener {
            holder.btn_layout_ll.visibility = View.GONE
            holder.declined_txt.visibility = View.VISIBLE
            holder.accepted_txt.visibility = View.GONE
            status = "declined"
            callbackInterface.passResultCallback(status)
        }

        if (itemList[position].status == "accepted"){
            holder.accepted_txt.visibility = View.VISIBLE
            holder.declined_txt.visibility = View.GONE
        }else if (itemList[position].status == "declined"){
            holder.declined_txt.visibility = View.VISIBLE
            holder.accepted_txt.visibility = View.GONE
        }

        holder.gender_txt.text = itemList[position].gender
        holder.name_txt.text = itemList[position].name
        holder.dob_txt.text = itemList[position].dob
        holder.phone_txt.text = itemList[position].phone
        holder.location_txt.text = itemList[position].location
        holder.email_value_txt.text = itemList[position].email
        Glide.with(context).load(itemList[position].picture).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var accepted_btn = itemView.findViewById<Button>(R.id.accepted_btn)
        var declined_btn = itemView.findViewById<Button>(R.id.declined_btn)
        var gender_txt = itemView.findViewById<TextView>(R.id.gender_value_txt)
        var name_txt = itemView.findViewById<TextView>(R.id.name_value_txt)
        var dob_txt = itemView.findViewById<TextView>(R.id.dob_value_txt)
        var phone_txt = itemView.findViewById<TextView>(R.id.phone_value_txt)
        var location_txt = itemView.findViewById<TextView>(R.id.location_value_txt)
        var btn_layout_ll = itemView.findViewById<LinearLayout>(R.id.btn_layout_ll)
        var accepted_txt = itemView.findViewById<TextView>(R.id.accepted_txt)
        var declined_txt = itemView.findViewById<TextView>(R.id.declined_txt)
        var email_value_txt = itemView.findViewById<TextView>(R.id.email_value_txt)

    }


    //callbackInterface for itemclick
    interface CallbackInterface{
        fun passResultCallback(status: String)
    }

}

