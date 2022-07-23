package com.example.myapplicationrecipi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecipi.R
import com.example.myapplicationrecipi.databinding.ListItemRecipeBinding
import com.example.myapplicationrecipi.model.Result

interface RecipeCallBack {
    fun onItemClicked(position: Int)
}

class RecipesAdapterSecond(
    private val listener: RecipeCallBack
) : ListAdapter<Result, RecipesAdapterSecond.RecipeViewHolder>(ItemDiff()) {

    class RecipeViewHolder(
        val binding: ListItemRecipeBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Result
        ) {
            binding.result = item
            binding.root.setOnClickListener {

            }
            binding.executePendingBindings()
        }
    }

    class ItemDiff : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ListItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }
    }
}