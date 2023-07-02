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
import com.example.house_analysis.R
import com.example.house_analysis.databinding.FragmentTasksBinding
import com.example.house_analysis.network.api.builtRequests.RequestRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


class TasksFragment : Fragment() {
    private var _binding : FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val networkRepository = RequestRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createTask.setOnClickListener {
            showDialog()
            lifecycleScope.launch {
                val tasks = requestGetTasks()
                //Тут (в теле lifecycleScope) и передавай полученн]ый масив в адаптер ресайклера
            }
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

//            postTaskRequest(address, from, to)
            dialog.dismiss()
        }

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}