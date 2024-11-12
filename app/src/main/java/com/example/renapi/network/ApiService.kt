package com.example.renapi.network

import com.example.renapi.model.Distilleries
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/distilleries_info/")
    fun getAllDistilleries(): Call<List<Distilleries>>

}