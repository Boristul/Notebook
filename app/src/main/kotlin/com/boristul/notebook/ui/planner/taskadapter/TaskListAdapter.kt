package com.boristul.notebook.ui.planner.taskadapter

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boristul.entity.TaskPoint
import com.boristul.uikit.TaskPointCard

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.ItemViewHolder>() {

    var tasks: List<TaskPoint> = listOf()
        set(value) {
            DiffUtil.calculateDiff(TaskListDiffCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    var onClickListener: ((TaskPoint) -> Unit)? = null
    var onLongClickListener: ((TaskPoint, view: View) -> Unit)? = null

    private var updatedItem: Long? = null

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(
        TaskPointCard(parent.context)
    ).apply {
        itemView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        card.onClickListener = {
            onClickListener?.invoke(tasks[bindingAdapterPosition])
            updatedItem = tasks[bindingAdapterPosition].id
        }
        card.onLongClickListener = { onLongClickListener?.invoke(tasks[bindingAdapterPosition], card) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        tasks[position].let { task ->
            holder.card.run {
                title = task.title

                if (task.id == updatedItem) {
                    setChecked(task.isCompleted)
                    updatedItem = null
                } else {
                    setChecked(task.isCompleted, false)
                }
            }
        }

    class ItemViewHolder(val card: TaskPointCard) : RecyclerView.ViewHolder(card)
}
