package com.example.demo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null,
    var title: String,
    var description: String,
    var completed: Boolean
)