package com.github.ddenaga.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(val tasks: List<String>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: TasksAdapter.ViewHolder, position: Int) {
        val task = tasks[position]
        holder.textView.text = task
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(adapterPosition)
                true
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

}