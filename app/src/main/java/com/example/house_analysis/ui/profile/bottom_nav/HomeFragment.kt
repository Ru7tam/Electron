package com.example.house_analysis.ui.profile.bottom_nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.TaskExecutor
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentHomeBinding
import com.example.house_analysis.ui.profile.bottom_nav.TaskList.TaskListAdapter
import com.example.house_analysis.ui.profile.bottom_nav.TaskList.Tasks

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        adapter = TaskListAdapter(listOf(
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), true),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),
            Tasks("ул. Красного Октября, 2", listOf(1,2,3,5,6,7,8,35,33,22,52), listOf(1,2,3,4), false),

        ))
        binding.rvTasks.adapter = adapter
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}