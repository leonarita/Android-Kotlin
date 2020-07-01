package com.example.app5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app5.R
import com.example.app5.entities.TaskEntity
import com.example.app5.entities.listener.OnTaskListFragmentInteractionListener
import com.example.app5.viewholder.TaskViewHolder

class TaskListAdapter (private val taskList: List<TaskEntity>, private val listener: OnTaskListFragmentInteractionListener) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Obtém item da lista
        val task: TaskEntity = taskList[position]
        holder.bindData(task, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context

        // Infla o layout da linha e faz uso na listagem
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, context)
    }
}