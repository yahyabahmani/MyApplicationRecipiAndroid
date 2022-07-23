package com.example.myapplicationrecipi.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplicationrecipi.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.myapplicationrecipi.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.myapplicationrecipi.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.myapplicationrecipi.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository@Inject constructor(@ApplicationContext private val context:Context) {
    private  object PreferenceKeys{
        val selectMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectMealTypeID = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectDietTypeID = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }
    // At the top level of your kotlin file:
    val  Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "food_Preferences")

    suspend fun savaMealAndDietType(mealType:String, mealTypeID:Int, dietType: String, dietTypeID:Int){
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.selectMealType] = mealType
            settings[PreferenceKeys.selectMealTypeID] = mealTypeID
            settings[PreferenceKeys.selectDietTypeID] = dietTypeID
            settings[PreferenceKeys.selectDietType] = dietType

        }
    }

    val readMealAndDietType:kotlinx.coroutines.flow.Flow<MealAndDietType> = context.dataStore.data
        .catch { exeption ->
            if (exeption is IOException) {
                emit(emptyPreferences())
            }else{
                throw exeption
            }

        }.map { preferencess ->
            val selectMealType = preferencess[PreferenceKeys.selectMealType] ?: "main cource"
            val selectMealTypeID = preferencess[PreferenceKeys.selectMealTypeID] ?: 0
            val selectDietType = preferencess[PreferenceKeys.selectDietType] ?:"gluten free"
            val selectDietTypeID = preferencess[PreferenceKeys.selectDietTypeID] ?: 0

            MealAndDietType(
                selectMealType,
                selectMealTypeID,selectDietType,selectDietTypeID
            )
        }

}
data class MealAndDietType(
    val selelctMealType:String,
    val selectMealTypeID:Int,
    val selectDietType:String,
    val selectDietTypeID:Int
)