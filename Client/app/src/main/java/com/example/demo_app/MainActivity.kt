package com.example.demo_app


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_app.databinding.DialogEditTaskBinding


class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextTitle = findViewById<TextView>(R.id.editTextTitle)
        val editTextDescription = findViewById<TextView>(R.id.editTextDescription)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTask)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)


        val adapter = TaskAdapter(
            onEdit = { task -> showEditTaskDialog(task) },
            onDelete = { task -> deleteTask(task) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        taskViewModel.tasks.observe(this) { tasks ->
            Log.d("MainActivity", "Tasks updated: $tasks")
            tasks?.let {
                adapter.submitList(it)
                Log.d("MainActivity", "Adapter updated with tasks: $it")
            }
        }

        buttonAddTask.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            val task = Task(title = title, description = description, completed = false)

            taskViewModel.addTask(task)
            Toast.makeText(
                this,
                "Задача добавлена",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("MainActivity", "Tasks updated: $task")
        }


    }

    private fun showEditTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = DialogEditTaskBinding.inflate(inflater)
        dialogLayout.editTextTitle.setText(task.title)
        dialogLayout.editTextDescription.setText(task.description)

        builder.setTitle("Edit Task")
            .setView(dialogLayout.root)
            .setPositiveButton("Save") { _, _ ->
                val updatedTask = task.copy(
                    title = dialogLayout.editTextTitle.text.toString(),
                    description = dialogLayout.editTextDescription.text.toString()
                )

                taskViewModel.updateTask(updatedTask.id!!, updatedTask)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteTask(task: Task) {
        taskViewModel.deleteTask(task.id!!)
    }


}