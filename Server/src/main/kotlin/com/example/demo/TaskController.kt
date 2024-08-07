package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController @Autowired constructor(
    private val taskRepository: TaskRepository
) {

    @GetMapping
    fun getAllTasks(): List<Task> = taskRepository.findAll()

    @PostMapping
    fun createTask(@RequestBody task: Task): Task = taskRepository.save(task)

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody task: Task): Task {
        if (!taskRepository.existsById(id)) {
            throw IllegalArgumentException("Task not found")
        }
        return taskRepository.save(task.copy(id = id))
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        taskRepository.deleteById(id)
    }
}
