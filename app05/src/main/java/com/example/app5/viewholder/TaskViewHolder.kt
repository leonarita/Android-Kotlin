package com.example.app5.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app5.R
import com.example.app5.entities.TaskEntity
import com.example.app5.entities.listener.OnTaskListFragmentInteractionListener
import com.example.app5.repository.cache.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextDueDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(taskEntity: TaskEntity, listener: OnTaskListFragmentInteractionListener) {

        mTextDescription.text = taskEntity.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(taskEntity.priorityId)
        mTextDueDate.text = taskEntity.dueDate

        // Atribui evento de click de detalhes
        mTextDescription.setOnClickListener({
            listener.onListClick(taskEntity.id)
        })

        // Atribui evento de remoção
        mTextDescription.setOnLongClickListener({
            showDialogConfirmation(taskEntity, listener)
            true
        })

        mImageTask.setOnClickListener({
            if (taskEntity.complete) {
                listener.onUncompleteClick(taskEntity.id)
            } else {
                listener.onCompleteClick(taskEntity.id)
            }
        })

        // Faz o tratamento para tarefas já completas
        if (taskEntity.complete) {
            this.mTextDescription.setTextColor(Color.GRAY)
            this.mImageTask.setImageResource(R.drawable.ic_done)
        }

    }

    /**
     * Confirma remoção
     */
    private fun showDialogConfirmation(taskEntity: TaskEntity, listener: OnTaskListFragmentInteractionListener) {
        AlertDialog.Builder(context)
            .setTitle(R.string.remocao_de_tarefa)
            .setMessage("Deseja realmente remover ${taskEntity.description}?")
            .setIcon(R.drawable.ic_remove)
            .setPositiveButton(R.string.sim, { dialogInterface: DialogInterface, i: Int -> listener.onDeleteClick(taskEntity.id) })
            .setNegativeButton(R.string.cancelar, null).show()
    }

}