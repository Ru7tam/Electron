package com.example.house_analysis.ui.profile.bottom_nav.TaskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.R

class TaskListAdapter (private val dataList: List<Tasks>):
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var isChecked: CheckBox = itemView.findViewById(R.id.isChecked)
        val street: TextView = itemView.findViewById(R.id.street)
        val apartmentLis: TextView = itemView.findViewById(R.id.apartmentList)
        val floorList: TextView = itemView.findViewById(R.id.floorList)

        fun bind(data:Tasks){

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
        val data = dataList[position]
        val apartmentStr = "${dataList[position].apartment[0]}-${dataList[position].apartment[dataList[position].apartment.size - 1]}"
        val floorStr = "${dataList[position].floors[0]}-${dataList[position].floors.size - 1}"

        holder.apply{
            isChecked.isChecked = dataList[position].isChecked
            street.text = dataList[position].street
            apartmentLis.text = apartmentStr
            floorList.text = floorStr
            bind(data)
        }
    }
}