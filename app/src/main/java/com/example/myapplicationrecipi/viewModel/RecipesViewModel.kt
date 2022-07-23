package com.example.myapplicationrecipi.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationrecipi.data.DataStoreRepository
import com.example.myapplicationrecipi.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class RecipesViewModel  @Inject constructor (application:Application
                                             ,private val dataStoreRepository:DataStoreRepository
                                             ):AndroidViewModel(application) {
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    private var defaultMealType = "main course"
    private var defaultDietType = "gluten free"


    fun saveMeal(mealType: String, mealid: Int, dietType: String, dietID: Int) =
        //question
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.savaMealAndDietType(mealType, mealid, dietType, dietID)

        }
            fun applyQuesry(): HashMap<String, String> {
                val query: HashMap<String, String> = HashMap()

                viewModelScope.launch {
                    readMealAndDietType.collect { value ->
                        defaultMealType = value.selelctMealType
                        defaultDietType = value.selectDietType
                    }
                }
                query["number"] = "12"
                query["apiKey"] = Constants.api_Key
                query["type"] = defaultMealType
                query["diet"] = defaultDietType
                query["addRecipeNutrition"] = "true"
                query["fillIngredients"] = "true"

                Log.d("yahyaquery",query.toString())
                return query
            }


        }
