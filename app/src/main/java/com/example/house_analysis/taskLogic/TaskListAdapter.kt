package com.example.house_analysis.taskLogic

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.house_analysis.network.model.response.TasksResponse

class TaskListAdapter (private var tasks: ArrayList<TasksResponse>):
    RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {

//    internal var taskData = TaskListData(tasks)
    private var checkedTasksId = arrayListOf<Int>()

    class TaskHolder(var taskItemView: TaskView):RecyclerView.ViewHolder(taskItemView){
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
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.updateWith(tasks[position])

        val listener = checkListener(holder.taskItemView, position)
        holder.taskItemView.checkBox?.setOnCheckedChangeListener(listener)
        holder.taskItemView.checkBox1?.setOnCheckedChangeListener(listener)
    }

    private fun checkListener(holder: TaskView, position: Int) =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            holder.checkBox?.isChecked = isChecked
            holder.checkBox1?.isChecked = isChecked

            if (isChecked) {
                if(!checkedTasksId.contains(tasks[position].taskId)) {
                    checkedTasksId.add(tasks[position].taskId)
                }
            }
            else checkedTasksId.remove(tasks[position].taskId)
        }
    internal fun removeTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }
}