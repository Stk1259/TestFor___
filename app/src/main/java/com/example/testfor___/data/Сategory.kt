package com.example.testfor___.data

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("image_url")val image_url: String,
)