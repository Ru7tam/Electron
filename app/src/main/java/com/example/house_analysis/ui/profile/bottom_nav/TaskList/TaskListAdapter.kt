package com.example.house_analysis.ui.profile.bottom_nav.TaskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R
import com.example.house_analysis.network.model.response.TasksResponse
import kotlin.math.sign

class TaskListAdapter (private val dataList: ArrayList<TasksResponse>):
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var isChecked = itemView.findViewById<CheckBox>(R.id.isChecked)
        val street = itemView.findViewById<TextView>(R.id.street)
        val appartmentLis = itemView.findViewById<TextView>(R.id.appartmentList)
        val floorList = itemView.findViewById<TextView>(R.id.floorList)

        fun bind(tasks:TasksResponse){
            street.text = tasks.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = dataList!![position]
        holder.bind(currentPosition)
    }
}