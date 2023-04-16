package com.example.shahrukhtask.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shahrukhtask.R
import com.example.shahrukhtask.UsersFragment
import com.example.shahrukhtask.databinding.ItemMainBinding
import com.example.shahrukhtask.model.User

class NewAdapter(val context : Fragment, val item:List<User>):RecyclerView.Adapter<NewAdapter.MainViewHolderItem>() {

    private lateinit var mListener:OnClickOfItem
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolderItem {
        val binding = DataBindingUtil.inflate<ItemMainBinding>(LayoutInflater.from(parent.context),
            R.layout.item_main,parent,false
        )
        mListener = context as UsersFragment
        return MainViewHolderItem(binding)
    }

    override fun onBindViewHolder(holder: NewAdapter.MainViewHolderItem, position: Int) {
        holder.binding.username.text = "${item[position].firstName} ${item[position].lastName}"
        holder.binding.userEmail.text = item[position].email
        Glide.with(holder.itemView)
            .load(item[position].avatar)
            .circleCrop()
            .into(holder.binding.userAvatar)
        holder.binding.cvItem.setOnClickListener {
            mListener.onClickOfItem(position)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class MainViewHolderItem(val binding: ItemMainBinding):RecyclerView.ViewHolder(binding.root)


    interface OnClickOfItem{
       fun onClickOfItem(position: Int)
    }
}