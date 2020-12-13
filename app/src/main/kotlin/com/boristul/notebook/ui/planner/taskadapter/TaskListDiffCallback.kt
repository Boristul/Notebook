package com.boristul.notebook.ui.planner.taskadapter

import androidx.recyclerview.widget.DiffUtil
import com.boristul.entity.TaskPoint

class TaskListDiffCallback(
    private val oldList: List<TaskPoint>,
    private val newList: List<TaskPoint>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
