package com.example.house_analysis.ui.profile.bottom_nav.tasks.recycler

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.TasksResponse

class TaskListAdapter (private val dataList: ArrayList<TasksResponse>):
    RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {


    inner class TaskHolder(var taskItemView: TaskView):RecyclerView.ViewHolder(taskItemView){
        @SuppressLint("SetTextI18n")
        fun updateWith(task: TasksResponse){
            taskItemView.apply {
                address?.text = task.title
                apartmentLis?.text = "${task.subtasksFrom}-${task.subtasksTo}"
                floorList?.text = "${task.floorsFrom}-${task.floorsTo}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val itemView = TaskView(parent.context)
        return TaskHolder(itemView)
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.updateWith(dataList[position])

        holder.taskItemView.checkBox?.setOnClickListener {

        }
    }
}