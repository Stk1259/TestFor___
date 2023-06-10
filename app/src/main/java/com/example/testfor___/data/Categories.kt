package com.example.testfor___.data

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("сategories")val categories: List<Category>
)