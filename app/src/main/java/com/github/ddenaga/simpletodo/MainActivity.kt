package com.github.ddenaga.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val tasks: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reference views from the main layout.
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val etTask = findViewById<EditText>(R.id.etTask)
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Binding the adapter to the recyclerView.
        val adapter = TasksAdapter(tasks)
        rvTasks.adapter = adapter
        rvTasks.layoutManager = LinearLayoutManager(this)
    }
}