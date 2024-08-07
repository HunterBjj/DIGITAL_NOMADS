package com.example.demo_app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            RetrofitClient.apiService.getAllTasks().enqueue(object : Callback<List<Task>> {
                override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                    if (response.isSuccessful) {
                        _tasks.value = response.body()
                    } else {
                        Log.e("TaskViewModel", "Error fetching tasks: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                    Log.e("TaskViewModel", "Network error fetching tasks", t)
                }
            })
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            RetrofitClient.apiService.createTask(task).enqueue(object : Callback<Task> {
                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    if (response.isSuccessful) {
                        fetchTasks()
                    } else {
                        Log.e("TaskViewModel", "Error adding task: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Log.e("TaskViewModel", "Network error adding task", t)
                }
            })
        }
    }

    fun updateTask(id: Long, task: Task) {
        viewModelScope.launch {
            RetrofitClient.apiService.updateTask(id, task).enqueue(object : Callback<Task> {
                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    if (response.isSuccessful) {
                        fetchTasks()
                    } else {
                        Log.e("TaskViewModel", "Error updating task: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Log.e("TaskViewModel", "Network error updating task", t)
                }
            })
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            RetrofitClient.apiService.deleteTask(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchTasks()
                    } else {
                        Log.e("TaskViewModel", "Error deleting task: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("TaskViewModel", "Network error deleting task", t)
                }
            })
        }
    }
}
