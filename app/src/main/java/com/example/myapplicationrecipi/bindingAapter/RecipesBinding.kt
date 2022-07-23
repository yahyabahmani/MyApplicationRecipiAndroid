package com.example.myapplicationrecipi.bindingAapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.myapplicationrecipi.model.FoodRecepi
import com.example.myapplicationrecipi.util.NetworkResult

class RecipesBinding {
    companion object{
        @BindingAdapter("readApiRsponse", requireAll = true)
        @JvmStatic
        fun errorImageViewVisible(imageView:ImageView,apiResponse:NetworkResult<FoodRecepi>?) {
            if (apiResponse is NetworkResult.Error) {
                imageView.visibility = View.VISIBLE
            }else{
                imageView.visibility = View.INVISIBLE
            }
        }
    }
}