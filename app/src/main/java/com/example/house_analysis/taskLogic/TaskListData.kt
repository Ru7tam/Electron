package com.example.house_analysis.taskLogic

import com.example.house_analysis.network.model.response.TasksResponse

class TaskListData(var tasks: ArrayList<TasksResponse>) {

    internal var checkedTasksId = arrayListOf<Int>()


}