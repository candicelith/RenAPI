package com.example.renapi.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/distilleries_info/")
    fun getAllDistilleries(): Call<Map<String, String>>

}