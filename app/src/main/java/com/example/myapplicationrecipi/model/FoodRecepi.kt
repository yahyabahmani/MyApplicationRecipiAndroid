package com.example.myapplicationrecipi.model


import com.google.gson.annotations.SerializedName

data class FoodRecepi(
    @SerializedName("results")
    val results: List<Result>,

    )