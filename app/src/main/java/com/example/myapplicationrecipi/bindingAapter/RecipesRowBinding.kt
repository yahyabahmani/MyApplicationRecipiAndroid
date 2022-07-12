package com.example.myapplicationrecipi.bindingAapter

import android.view.View
import android.webkit.JavascriptInterface
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.myapplicationrecipi.R

class RecipesRowBinding {
    companion object{
        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView:ImageView,imgUrl:String){
            imageView.load(imgUrl){
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOflikees")
        @JvmStatic
        fun setNumberOflikees(textView: TextView, likes:Int){
            textView.text =  likes.toString()
        }

        @BindingAdapter("setNumberOfMiniute")
        @JvmStatic
        fun setNumberOfiniutet(textView: TextView, min:Int){
            textView.text =  min.toString()
        }

        @BindingAdapter("applyVeaga")
        @JvmStatic
        fun applyVeaga(view:View, veagn:Boolean){
            if (veagn){
                when(view){
                    is TextView->{
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                        )
                    }
                }

            }
        }


    }
}