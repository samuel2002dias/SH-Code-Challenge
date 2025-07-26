package com.example.cats

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import com.squareup.moshi.Moshi

/**
 * Retrofit API service for fetching cat breeds.
 */
interface CatApiService {
    /**
     * Gets the list of cat breeds from the API.
     */
    @GET("v1/breeds")
    suspend fun getBreeds(): List<Breed>
}

/**
 * Singleton object for creating the Retrofit client.
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.thecatapi.com/"
    private const val API_KEY = "live_NpB2lhTv8csM0MtJGbAS8xz3L0oVuqNdRpniImMJYRV4s5qoVBxOiawssf5imFgK"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", API_KEY)
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val moshi = Moshi.Builder().build()

    /**
     * Lazily initialized API service.
     */
    val api: CatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CatApiService::class.java)
    }
}