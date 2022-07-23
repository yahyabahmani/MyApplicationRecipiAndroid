package com.example.myapplicationrecipi.di

import com.example.myapplicationrecipi.data.network.FoodRecipesApi
import com.example.myapplicationrecipi.util.Constants.Companion.base_url
import com.example.myapplicationrecipi.model.FoodRecepi
import com.example.myapplicationrecipi.ui.Test
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providerHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun providerConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providerRetrofitInstanc(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ):
            Retrofit {
        return Retrofit.Builder().baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providerApiService(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }

    @Provides
    fun providesTest(

    ):Test = Test(1,2,3)

}