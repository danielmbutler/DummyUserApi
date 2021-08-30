package com.dbtechprojects.dummyuserapi.ui.adapters


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.dbtechprojects.dummyuserapi.databinding.UserItemBinding
import com.dbtechprojects.dummyuserapi.models.User
import com.dbtechprojects.dummyuserapi.util.ImageLoader
import java.util.ArrayList

class UserListAdapter(

) : RecyclerView.Adapter<UserListAdapter.RVViewHolder>() {
    private var dataSet: List<User> = ArrayList<User>()
    @RequiresApi(api = Build.VERSION_CODES.N)

    fun updateList(value: List<User>) {
        dataSet = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        return RVViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class RVViewHolder internal constructor(var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            currentItem: User,
        ) {
            (currentItem.firstName + " " + currentItem.lastName).also { binding.userItemNameTv.text = it }
            ImageLoader.loadImage(itemView.context, binding.userItemImage, currentItem.picture)

        }

        companion object {
            fun getViewHolder(parent: ViewGroup): RVViewHolder {
                val binding: UserItemBinding = UserItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RVViewHolder(binding)
            }
        }

    }

//    interface onClickListener {
//        fun onClick(user: User?)
//    }


}