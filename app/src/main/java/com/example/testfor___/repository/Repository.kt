package com.example.testfor___.repository

import android.util.Log
import com.example.testfor___.data.Category
import com.example.testfor___.network.apiService

class Repository {
    suspend fun getCategories(): List<Category> {
        val categories = apiService.getRepos().categories
        Log.d("categories", apiService.getRepos().toString())
        return categories
    }
}