package com.example.house_analysis.taskLogic

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.Subtask

class SubtasksListAdapter(private val tasks: List<Subtask>):
    RecyclerView.Adapter<SubtasksListAdapter.MyViewHolder>() {

    class MyViewHolder(var taskView: TaskView): RecyclerView.ViewHolder(taskView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}