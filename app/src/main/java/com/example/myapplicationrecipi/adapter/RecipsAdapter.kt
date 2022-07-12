package com.example.myapplicationrecipi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecipi.databinding.ListItemRecipeBinding
import com.example.myapplicationrecipi.model.FoodRecepi
import com.example.myapplicationrecipi.model.Result
import com.example.myapplicationrecipi.util.RecipesDiffUtill

class RecipsAdapter:RecyclerView.Adapter<RecipsAdapter.MyViewHolder>() {
    private val recipes = ArrayList<Result>()
    class MyViewHolder(private val binding:ListItemRecipeBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding  = ListItemRecipeBinding.inflate(layoutInflater,parent,false)
                return  MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return  MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val current = recipes[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return  recipes.size
    }

    fun setData(newData:FoodRecepi){
        val recipiDiffutill = RecipesDiffUtill(this.recipes,newData.results)
        val diffUtillResult =  DiffUtil.calculateDiff(recipiDiffutill)
        recipes.clear()
        recipes.addAll(newData.results)
        diffUtillResult.dispatchUpdatesTo(this )
    }
}