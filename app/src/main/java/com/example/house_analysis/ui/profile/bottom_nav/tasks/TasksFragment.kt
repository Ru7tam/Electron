package com.example.house_analysis.ui.profile.bottom_nav.tasks

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentTasksBinding
import com.example.house_analysis.network.api.builtRequests.RequestRepository
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.ui.profile.bottom_nav.tasks.recycler.TaskListAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class TasksFragment : Fragment() {
    private lateinit var binding : FragmentTasksBinding

    private lateinit var recyclerView: RecyclerView

    private val networkRepository = RequestRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    private fun ifNoTasks(tasks: ArrayList<TasksResponse>) {
        if (tasks.isEmpty()) {
            binding.ifTasksNotExists.visibility = View.VISIBLE
            binding.ifTasksExists.visibility = View.GONE
        }
        else {
            binding.ifTasksNotExists.visibility = View.GONE
            binding.ifTasksExists.visibility = View.VISIBLE
        }
    }

    private fun initRecycler() {
        recyclerView = binding.rvTasks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val tasks = requestGetTasks()
            recyclerView.apply {
                adapter = TaskListAdapter(tasks)
                // Todo(Имплементировать интерфейс отслеживания кликов по item)
            }
            ifNoTasks(tasks)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.createTask.setOnClickListener {
            showDialog()

//            requestGetTaskWithSubtasks(25)
        }
    }

    private suspend fun requestGetTasks() = networkRepository.getTasks()

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_create_new_task)

        // Set dialog width and height
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT // Set height to wrap content

        dialog.findViewById<ImageView>(R.id.closeDialog).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.createTask).setOnClickListener {
            val address = dialog.findViewById<TextInputEditText>(R.id.text_address).text.toString()
            val from = dialog.findViewById<TextInputEditText>(R.id.text_from).text.toString().toInt()
            val to = dialog.findViewById<TextInputEditText>(R.id.text_to).text.toString().toInt()

            networkRepository.createTask(TaskRequestModel(address, from, to))
            dialog.dismiss()
        }

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

}