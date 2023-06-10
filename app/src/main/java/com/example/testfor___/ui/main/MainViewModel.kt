package com.example.testfor___.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testfor___.data.Category
import com.example.testfor___.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val repository = Repository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoryList = withContext(Dispatchers.IO) {
                    repository.getCategories()
                }
                _categories.value = categoryList
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        }
    }
}