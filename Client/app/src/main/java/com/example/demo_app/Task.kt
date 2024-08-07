package com.example.demo_app

data class Task (
    val id: Long? = null,
    val title: String,
    val description: String,
    val completed: Boolean
)