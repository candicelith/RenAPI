package com.example.renapi.model

import com.google.gson.annotations.SerializedName

data class Distilleries(
    @SerializedName("name")
    val name: String,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("whiskybase_whiskies")
    val whiskies: String?,

    @SerializedName("whiskybase_votes")
    val votes: String?,

    @SerializedName("whiskybase_rating")
    val rating: String?
)

