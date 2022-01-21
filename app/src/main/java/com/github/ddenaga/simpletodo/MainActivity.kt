package com.github.ddenaga.simpletodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

        // Long click listener for removing items in the RecyclerView.
        val onLongClickListener = object : TasksAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                adapter.notifyItemRemoved(position)
                tasks.removeAt(position)
                // Save tasks.
                savePersistentData()
            }
        }

        // Click listener for editting items in the RecyclerView.
        val onClickListener = object : TasksAdapter.OnClickListener {
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val position = result.data?.getIntExtra("position", 0) as Int
                    val taskText = result.data?.getStringExtra("taskText") as String

                    if (taskText.isNotBlank()) {
                        tasks[position] = taskText
                        adapter.notifyItemChanged(position)
                    }
                    else {
                        adapter.notifyItemRemoved(position)
                        tasks.removeAt(position)
                    }
                    savePersistentData()
                }
            }

            override fun onItemClicked(position: Int) {
                val i = Intent(this@MainActivity, EditActivity::class.java)
                i.putExtra("taskText", tasks[position])
                i.putExtra("position", position)
                startForResult.launch(i)
            }
        }

        // Load persistent data.
        loadPersistentData()

        // Reference views from the main layout.
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val etTask = findViewById<EditText>(R.id.etTask)
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Binding the adapter to the RecyclerView.
        adapter = TasksAdapter(tasks, onLongClickListener, onClickListener)
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