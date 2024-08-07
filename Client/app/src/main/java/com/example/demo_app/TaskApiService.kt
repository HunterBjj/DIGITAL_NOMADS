package com.example.demo_app

import retrofit2.Call
import retrofit2.http.*

interface TaskApiService {
    @GET("tasks")
    fun getAllTasks(): Call<List<Task>>

    @POST("tasks")
    fun createTask(@Body task: Task): Call<Task>

    @PUT("tasks/{id}")
    fun updateTask(@Path("id") id: Long, @Body task: Task): Call<Task>

    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id") id: Long): Call<Void>
}