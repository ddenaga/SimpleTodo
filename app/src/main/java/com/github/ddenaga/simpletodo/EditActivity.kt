package com.github.ddenaga.simpletodo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        this.title = "Edit Task"

        val etEditTask = findViewById<EditText>(R.id.etEditTask)
        val btnSave = findViewById<Button>(R.id.btnSave)

        etEditTask.setText(intent.getStringExtra("taskText"))

        btnSave.setOnClickListener {
            val data = Intent()
            data.putExtra("position", intent.getIntExtra("position", 0))
            data.putExtra("taskText", etEditTask.text.toString())
            setResult(RESULT_OK, data)
            finish()
        }
    }
}