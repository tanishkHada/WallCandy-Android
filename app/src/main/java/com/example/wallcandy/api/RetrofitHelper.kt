package com.example.wallcandy.api

import com.example.wallcandy.utilities.MyConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    // Volatile to ensure visibility of changes to different threads
    @Volatile
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: buildRetrofit().also {
                retrofit = it
            }
        }
    }

    private fun buildRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", MyConstants.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(MyConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}