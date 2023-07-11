package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentTasksBinding
import com.example.house_analysis.network.api.requests.RequestRepository
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.taskLogic.ItemClickSupport
import com.example.house_analysis.taskLogic.TaskListAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class TasksFragment : Fragment() {
    private lateinit var binding : FragmentTasksBinding
    private lateinit var recyclerView: RecyclerView
    private val networkRepository = RequestRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.createTask.setOnClickListener {
            showTaskCreatorDialog()
        }
    }

    private fun initRecycler() {
        recyclerView = binding.rvTasks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setItemRecyclerListeners()
        fillRecycler()
    }

    private fun setItemRecyclerListeners() {
        ItemClickSupport.addTo(recyclerView)
            ?.setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {

                }
            })
            ?.setOnItemLongClickListener(object: ItemClickSupport.OnItemLongClickListener {
                override fun onItemLongClicked(recyclerView: RecyclerView?, position: Int, v: View?): Boolean {

                    return true
                }
            })
    }

    private fun fillRecycler() {
        lifecycleScope.launch {
            val tasks = networkRepository.getTasks()
            recyclerView.adapter = TaskListAdapter(tasks)
            ifNoTasks(tasks)
        }
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

    private fun showTaskCreatorDialog(){
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

            lifecycleScope.launch {
                val taskId = networkRepository.createTask(TaskRequestModel(address, from, to))
                val task = networkRepository.getTask(taskId)
                val s = 1
            }
            dialog.dismiss()
        }

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

}