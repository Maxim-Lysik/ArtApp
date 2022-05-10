package com.example.artapp.toroom

import com.example.artapp.data.response.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("repositories")
    fun getDataFromApi(@Query("q") query: String): Call<City>
}

