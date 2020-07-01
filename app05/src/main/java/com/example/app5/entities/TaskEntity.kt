package com.example.app5.entities

data class TaskEntity (
    val id: Int,
    val userId: Int,
    var priorityId: Int = 0,
    var description: String,
    var dueDate: String,
    var complete: Boolean = false
)