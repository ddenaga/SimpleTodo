package com.github.ddenaga.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var tasks = mutableListOf<String>()
    lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Long click listener for items in the RecyclerView.
        val onLongClickListener = object : TasksAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                tasks.removeAt(position)
                adapter.notifyDataSetChanged()
                // Save tasks.
                savePersistentData()
            }
        }

        // Load persistent data.
        loadPersistentData()

        // Reference views from the main layout.
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val etTask = findViewById<EditText>(R.id.etTask)
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Binding the adapter to the RecyclerView.
        adapter = TasksAdapter(tasks, onLongClickListener)
        rvTasks.adapter = adapter
        rvTasks.layoutManager = LinearLayoutManager(this)

        // Add items by listening to the button and reading the edit text view.
        btnAdd.setOnClickListener {
            if (etTask.text.isNotBlank()) {
                // Add task to the list and update the RecyclerView.
                tasks.add(etTask.text.toString())
                adapter.notifyItemInserted(tasks.size - 1)
                // Save tasks.
                savePersistentData()
            }

            // Clear the text field.
            etTask.text.clear()
        }
    }

    fun getPersistentData() : File {
        return File(filesDir, "data.txt")
    }

    fun loadPersistentData() {
        try {
            tasks = FileUtils.readLines(getPersistentData(), Charset.defaultCharset())
        }
        catch (ioException : IOException) {
            ioException.printStackTrace()
        }
    }

    fun savePersistentData() {
        try {
            FileUtils.writeLines(getPersistentData(), tasks)
        }
        catch (ioException : IOException) {
            ioException.printStackTrace()
        }
    }
}