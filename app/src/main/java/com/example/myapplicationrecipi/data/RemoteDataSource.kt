package com.example.myapplicationrecipi.data

import com.example.myapplicationrecipi.data.network.FoodRecipesApi
import com.example.myapplicationrecipi.model.FoodRecepi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor (
    private  val foodRecipesApi: FoodRecipesApi
        ){
    // Question
    suspend fun getRecipes(queries: Map<String,String>):Response<FoodRecepi>{
        return  foodRecipesApi.getRecipes(queries)
    }
}