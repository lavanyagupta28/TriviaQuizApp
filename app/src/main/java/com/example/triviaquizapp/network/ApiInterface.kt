package com.example.triviaquizapp.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api.php")
    suspend fun getData(
        @Query("amount") amount: Int,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?
    ): Response<ApiResponse>

    companion object {
        var apiInterface: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if (apiInterface == null) {
                apiInterface = Retrofit.Builder()
                    .baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface::class.java)
            }
            return apiInterface!!
        }
    }
}