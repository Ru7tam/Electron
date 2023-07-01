package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentTasksHomeBinding
import com.example.house_analysis.network.api.RequestRepositoryProvider
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.ui.profile.bottom_nav.TaskList.TaskListAdapter
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TasksHomeFragment : Fragment() {
    private var _binding : FragmentTasksHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView : RecyclerView

    private var stateVarIsEmpty : Boolean = true

    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTasksHomeBinding.inflate(inflater, container, false)
        if (requestGetTasks().isEmpty()) {
            binding.ifTasksNotExists.visibility = View.VISIBLE
            binding.ifTasksExists.visibility = View.GONE

            d("Network", "EMPTY")
        }
        else {
            binding.ifTasksNotExists.visibility = View.GONE
            binding.ifTasksExists.visibility = View.VISIBLE
            d("Network", "SOME")
        }



        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.createTask.setOnClickListener {
            showDialog()
        }

        recyclerView = binding.rvTasks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TaskListAdapter(requestGetTasks())

    }

    private fun requestGetTasks()
    : ArrayList<TasksResponse>
    {
        var tasks = arrayListOf<TasksResponse>()
        networkRepository.getTasks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d("Network", result.toString())
                    tasks = result
                }, { error ->
                    Exception(error).printStackTrace()
                }
            )
        return tasks
    }

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

            postTaskRequest(address, from, to)
            dialog.dismiss()
        }

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun stateGetRequest(state: ArrayList<TasksResponse>){
        stateVarIsEmpty = !state.isEmpty()
    }
    private fun postTaskRequest(address: String, from: Int, to: Int) {
        networkRepository.createTask(title = address, from = from, to = to)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d("Network", result.toString())
                }, { error ->
                    d("Network", error.toString())
                    Exception(error).printStackTrace()
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}