package com.example.myapplicationrecipi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplicationrecipi.data.Repository
import com.example.myapplicationrecipi.model.FoodRecepi
import com.example.myapplicationrecipi.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.temporal.TemporalQueries
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecepi>> = MutableLiveData() // Question
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipisResponse(response)
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes Not Found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun handleFoodRecipisResponse(response: Response<FoodRecepi>): NetworkResult<FoodRecepi>? {
        when {
            response.message().toString().contains("timeote") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key limite")
            }
            response.body()?.results.isNullOrEmpty() -> { // Question
                return NetworkResult.Error("RecipesNot found")

            }
            response.isSuccessful -> {
                val foodRecepi = response.body()
                return NetworkResult.Success(foodRecepi!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    fun hasInternetConnection(): Boolean {
        val connectiveManger = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activateNetwork = connectiveManger.activeNetwork ?: return false
        val capabilities = connectiveManger.getNetworkCapabilities(activateNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}