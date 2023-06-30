package com.example.house_analysis.ui.profile.bottom_nav

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.TaskExecutor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentHomeBinding
import com.example.house_analysis.network.api.RequestRepositoryProvider
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.ui.profile.bottom_nav.TaskList.TaskListAdapter
import com.example.house_analysis.ui.profile.bottom_nav.TaskList.Tasks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()

//    Tasks
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskListAdapter(
            requestGetTasks())
        adapter.notifyDataSetChanged()
//        adapter = TaskListAdapter(listOf(
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
//
//        ))
//        binding.rvTasks.adapter = adapter
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun requestGetTasks(): ArrayList<TasksResponse> {
        var tasks = arrayListOf<TasksResponse>()
        networkRepository.getTasks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    d("Network", result.toString())
                    tasks = result
                }, { error ->
                    d("Network", error.toString())
                    Exception(error).printStackTrace()
                }
            )
        return tasks
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}