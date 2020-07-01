package com.example.app5.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app5.R
import com.example.app5.adapter.TaskListAdapter
import com.example.app5.business.TaskBusiness
import com.example.app5.constants.TaskConstants
import com.example.app5.entities.TaskEntity
import com.example.app5.entities.listener.OnTaskListFragmentInteractionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment :
    Fragment(), View.OnClickListener {

    private var mFilter: Int = 0
    private lateinit var mContext: Context
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mOnTaskListFragmentInteractionListener: OnTaskListFragmentInteractionListener
    private lateinit var mRecyclerTaskList: RecyclerView

    /**
     * Método estático - Não é necessário instanciar a fragment
     * */
    companion object {
        fun newInstance(filter: Int): TaskListFragment {

            // Instancia fragment
            val fragment = TaskListFragment()

            // Adiciona parâmetros
            val args = Bundle()
            args.putInt(TaskConstants.TASKFILTER.FILTERKEY, filter)
            fragment.arguments = args

            // Retorna
            return fragment
        }
    }

    /**
     * Faz a criação da Fragment
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica qual o filtro que foi passado para a listagem
        if (arguments != null) {
            mFilter = arguments!!.getInt(TaskConstants.TASKFILTER.FILTERKEY, 0)
        }
    }

    /**
     * Faz a criação visual da fragment - Semelhante ao onResume de uma Activity
     * */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Infla o layout
        val rootView: View = inflater!!.inflate(R.layout.activity_task_list_fragment, container, false)

        // Incializa as variáveis
        mContext = rootView.context
        mTaskBusiness = TaskBusiness(mContext)

        // Necessário buscar os elementos de interface através do findViewById. Não funciona Kotlin-Extensions
        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)

        // Inicializa listener
        createInteractionListener()

        // 1 - Obter a recyclerView
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 - Definir adapter passando listagem de itens
        val taskListAdapter = TaskListAdapter(mutableListOf(), mOnTaskListFragmentInteractionListener)
        mRecyclerTaskList.adapter = taskListAdapter

        // 3 - Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        // Retorna View
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    /**
     * Trata evento dos elementos
     * */
    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.floatAddTask) {
            val intent = Intent(mContext, TaskFormActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Carrega tarefas
     */
    private fun loadTasks() {

        // Carrega lista de tarefas
        val listTaskEntity: MutableList<TaskEntity> = mTaskBusiness.getList(mFilter)

        // Inicializa o adapter com registros atualizados
        mRecyclerTaskList.adapter = TaskListAdapter(listTaskEntity, mOnTaskListFragmentInteractionListener)

        // Faz a contagem de quantas tarefas estão completas
        val completed: Int = listTaskEntity.count { it.complete }

        // Atualiza número de registros
        (activity as MainActivity).updateTaskCount(listTaskEntity.size, completed)
    }

    /**
     * Interação com a listagem de tarefas
     */
    private fun createInteractionListener() {
        mOnTaskListFragmentInteractionListener = object : OnTaskListFragmentInteractionListener {
            override fun onListClick(taskId: Int) {
                val bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(mContext, TaskFormActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                Toast.makeText(mContext, getString(R.string.tarefa_removida_com_sucesso), Toast.LENGTH_LONG).show()
                loadTasks()
            }

            override fun onCompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, true)
                loadTasks()
            }

            override fun onUncompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, false)
                loadTasks()
            }
        }
    }
}