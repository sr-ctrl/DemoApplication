package com.example.shahrukhtask.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shahrukhtask.R
import com.example.shahrukhtask.databinding.ItemMainBinding
import com.example.shahrukhtask.model.User


class UserAdapter(val item:List<User>):RecyclerView.Adapter<UserAdapter.MainItem>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItem {
       val binding = DataBindingUtil.inflate<ItemMainBinding>(
           LayoutInflater.from(parent.context),
           R.layout.item_main,parent,false)
        return MainItem(binding)
    }


    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount:${item.count()} ")
          return item.count()
    }

    override fun onBindViewHolder(holder: MainItem, position: Int) {
        Glide.with(holder.itemView)
            .load(item[position].avatar)
            .into(holder.binding.userAvatar)
        holder.binding.username.text = item[position].firstName + item[position].lastName
        holder.binding.userEmail.text = item[position].email
    }

    inner class MainItem(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

}


