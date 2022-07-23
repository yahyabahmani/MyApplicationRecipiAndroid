package com.example.myapplicationrecipi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecipi.databinding.ListItemRecipeBinding
import com.example.myapplicationrecipi.model.FoodRecepi
import com.example.myapplicationrecipi.model.Result
import com.example.myapplicationrecipi.util.RecipesDiffUtill

class RecipsAdapter : RecyclerView.Adapter<RecipsAdapter.MyViewHolder>() {
    private val recipes = ArrayList<Result>()

    class MyViewHolder(private val binding: ListItemRecipeBinding) ://question
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result,position: Int) {
//            if (position == 2){
//                binding.LikeTextView
//            }
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = recipes[position]
        holder.bind(current,position)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecepi) {
        val recipiDiffutill = RecipesDiffUtill(this.recipes, newData.results)
        val diffUtillResult = DiffUtil.calculateDiff(recipiDiffutill)
        recipes.clear()
        recipes.addAll(newData.results)
        diffUtillResult.dispatchUpdatesTo(this)
    }
}